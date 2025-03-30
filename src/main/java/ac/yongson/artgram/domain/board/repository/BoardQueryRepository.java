package ac.yongson.artgram.domain.board.repository;

import ac.yongson.artgram.domain.board.dto.BoardResponseDTO;
import ac.yongson.artgram.domain.board.entity.QBoard;
import ac.yongson.artgram.domain.board.entity.QBoardLinkImage;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static ac.yongson.artgram.domain.comment.entity.QComment.comment;
import static ac.yongson.artgram.domain.image.entity.QImage.image;
import static ac.yongson.artgram.domain.member.entity.QMember.member;
import static com.querydsl.core.group.GroupBy.groupBy;

@RequiredArgsConstructor
@Repository
public class BoardQueryRepository {
        private final JPAQueryFactory jpaQueryFactory;
        private final QBoard board = QBoard.board;
        private final QBoardLinkImage boardLinkImage = QBoardLinkImage.boardLinkImage;

        public List<BoardResponseDTO.Post> getMainPostList() {
                return jpaQueryFactory
                        .from(board)
                        .leftJoin(board.member)
                        .leftJoin(board.member.image)
                        .leftJoin(board.boardImages, boardLinkImage)
                        .orderBy(board.boardId.desc())
                        .transform(
                                groupBy(board.boardId).list(
                                        Projections.fields(BoardResponseDTO.Post.class,
                                                board.boardId,
                                                board.title,
                                                board.content,
                                                board.member.memberName,
                                                board.member.image.imageUrl.as("profileImageUrl"),
                                                board.likeCount,
                                                board.comments.size().as("commentCount"),
                                                GroupBy.list(boardLinkImage.image.imageId).as("imageIds"))));
        }

        public List<BoardResponseDTO.Post> getMainMyPostList(Long memberId) {
                return jpaQueryFactory
                        .from(board)
                        .leftJoin(board.member)
                        .leftJoin(board.member.image)
                        .leftJoin(board.boardImages, boardLinkImage)
                        .where(board.memberId.eq(memberId))
                        .orderBy(board.boardId.desc())
                        .transform(
                                groupBy(board.boardId).list(
                                        Projections.fields(BoardResponseDTO.Post.class,
                                                board.boardId,
                                                board.title,
                                                board.content,
                                                board.member.memberName,
                                                board.member.image.imageUrl.as("profileImageUrl"),
                                                board.likeCount,
                                                board.comments.size().as("commentCount"),
                                                GroupBy.list(boardLinkImage.image.imageId)
                                                                .as("imageIds"))));
        }

        public List<BoardResponseDTO.Post> getBoardLikeList(List<Long> boardIds) {
                return jpaQueryFactory
                        .from(board)
                        .leftJoin(board.member)
                        .leftJoin(board.member.image)
                        .leftJoin(board.boardImages, boardLinkImage)
                        .where(board.boardId.in(boardIds))
                        .orderBy(board.boardId.desc())
                        .transform(
                                groupBy(board.boardId).list(
                                        Projections.fields(BoardResponseDTO.Post.class,
                                                board.boardId,
                                                board.title,
                                                board.content,
                                                board.member.memberName,
                                                board.member.image.imageUrl.as("profileImageUrl"),
                                                board.likeCount,
                                                board.comments.size().as("commentCount"),
                                                GroupBy.list(boardLinkImage.image.imageId)
                                                        .as("imageIds"))));
        }

        public BoardResponseDTO.BoardInfo getBoard(Long boardId) {
                List<BoardResponseDTO.BoardInfo> result = jpaQueryFactory
                        .from(board)
                        .distinct()
                        .leftJoin(board.member)
                        .leftJoin(board.member.image)
                        .leftJoin(board.comments, comment)
                        .leftJoin(comment.member)
                        .leftJoin(comment.member.image)
                        .leftJoin(board.boardImages, boardLinkImage)
                        .where(board.boardId.eq(boardId))
                        .transform(
                                groupBy(board.boardId).list(
                                        Projections.fields(BoardResponseDTO.BoardInfo.class,
                                                board.boardId,
                                                board.title,
                                                board.content,
                                                board.member.memberName.as("boardCreateName"),
                                                board.member.image.imageUrl.as("boardCreateProfileImageUrl"),
                                                board.likeCount,
                                                GroupBy.list(
                                                        Projections.fields(
                                                                BoardResponseDTO.BoardInfo.CommentInfo.class,
                                                                comment.commentId,
                                                                comment.member.memberName.as("commentCreateName"),
                                                                comment.member.image.imageUrl.as("commentCreateProfileImageUrl"),
                                                                comment.content,
                                                                comment.createdDateTime.as("commentCreateTime"))
                                                        )
                                                        .as("comments")
                                        )
                                )
                        );
                return result.isEmpty() ? null : result.get(0);
        }
}
