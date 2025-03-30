package ac.yongson.artgram.domain.board.service.impl;

import ac.yongson.artgram.domain.board.dto.BoardRequestDTO;
import ac.yongson.artgram.domain.board.dto.BoardResponseDTO;
import ac.yongson.artgram.domain.board.entity.Board;
import ac.yongson.artgram.domain.board.entity.BoardLike;
import ac.yongson.artgram.domain.board.entity.BoardLinkImage;
import ac.yongson.artgram.domain.board.repository.BoardLikeRepository;
import ac.yongson.artgram.domain.board.repository.BoardLinkImageRepository;
import ac.yongson.artgram.domain.board.repository.BoardQueryRepository;
import ac.yongson.artgram.domain.board.repository.BoardRepository;
import ac.yongson.artgram.domain.board.service.BoardService;
import ac.yongson.artgram.domain.image.entity.Image;
import ac.yongson.artgram.domain.image.service.ImageService;
import ac.yongson.artgram.global.exception.Exception400;
import ac.yongson.artgram.global.exception.Exception500;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ImageService imageService;
    private final BoardQueryRepository boardQueryRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardLinkImageRepository boardLinkImageRepository;

    @Override
    public void saveBoard(BoardRequestDTO.SaveBoard saveBoard, List<MultipartFile> files, Long memberId) {
        List<Image> images = imageService.registerImages(files);
        try {
            Board board = saveBoard.toEntity(memberId);
            Board boardPS = boardRepository.save(board);
            saveBoardLinkImages(boardPS,images);
        } catch (Exception e) {
            e.printStackTrace();
            imageService.deleteImages(images);
            throw new Exception500("board", "게시물 등록 실패");
        }
    }

    @Override
    public List<BoardResponseDTO.Post> getBoards(Long memberId) {
        List<BoardResponseDTO.Post> posts = boardQueryRepository.getMainPostList();
        for(BoardResponseDTO.Post post : posts){
            if(getBoardLikePS(memberId,post.getBoardId()) != null){
                post.isLike();
            }
        }
        return posts;
    }
    @Override
    public List<BoardResponseDTO.Post> getMyBoards(Long memberId){
        return boardQueryRepository.getMainMyPostList(memberId);
    }
    @Override
    public List<BoardResponseDTO.Post> getBoardLikeList(Long memberId){
        List<Long> boardIds = boardLikeRepository.findAllByMemberId(memberId);
        return boardQueryRepository.getBoardLikeList(boardIds);
    }
    @Override
    public BoardResponseDTO.BoardInfo getBoard(Long memberId, Long boardId){
        BoardResponseDTO.BoardInfo boardInfo= boardQueryRepository.getBoard(boardId);
        List<BoardLinkImage> boardLinkImages = boardLinkImageRepository.findByBoardId(boardId);
        List<String> images = new ArrayList<>();
        for(BoardLinkImage boardLinkImage:boardLinkImages){
            images.add(boardLinkImage.getImage().getImageUrl());
        }
        boardInfo.setImages(images);
        if(getBoardLikePS(memberId,boardInfo.getBoardId()) != null){
            boardInfo.isLike();
        }
        return boardInfo;
    }
    @Override
    @Transactional
    public void saveBoardLike(Long memberId, Long boardId){
        Board boardPS = getBoardPS(boardId);
        boardPS.incrementLikeCount();
        if(getBoardLikePS(memberId,boardId) != null){
            throw new Exception400("board", "이미 좋아요를 눌렀습니다.");
        }
        BoardLike boardLike = BoardLike.builder()
                .memberId(memberId)
                .boardId(boardId)
                .build();
        boardLikeRepository.save(boardLike);
    }

    @Override
    @Transactional
    public void deleteBoardLike(Long memberId, Long boardId){
        Board boardPS = getBoardPS(boardId);
        boardPS.decrementLikeCount();

        BoardLike boardLikePS = getBoardLikePS(memberId,boardId);
        if(boardLikePS == null){
            throw new Exception400("board", "좋아요를 누르지 않았습니다.");
        }
        boardLikeRepository.delete(boardLikePS);
    }
    private BoardLike getBoardLikePS(Long memberId, Long boardId){
        return boardLikeRepository.findByMemberIdAndBoardId(memberId,boardId);
    }
    private Board getBoardPS(Long boardId){
        return boardRepository.findById(boardId).orElseThrow(
                () ->new Exception400("board","해당하는 게시글이 없습니다.")
        );
    }

    private void saveBoardLinkImages(Board board, List<Image> images){
        List<BoardLinkImage> boardLinkImages = new ArrayList<>();
        for (Image image : images) {
            BoardLinkImage boardLinkImage = BoardLinkImage
                    .builder()
                    .board(board)
                    .image(image)
                    .build();
            boardLinkImages.add(boardLinkImage);
        }
        boardLinkImageRepository.saveAll(boardLinkImages);
    }
}
