package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = 455343204L;

    public static final QBoard board = new QBoard("board");

    public final StringPath boardContent = createString("boardContent");

    public final StringPath boardFile = createString("boardFile");

    public final NumberPath<Integer> boardFlag = createNumber("boardFlag", Integer.class);

    public final NumberPath<Integer> boardHeartnum = createNumber("boardHeartnum", Integer.class);

    public final StringPath boardTitle = createString("boardTitle");

    public final NumberPath<Integer> boardViews = createNumber("boardViews", Integer.class);

    public final StringPath boardWritetime = createString("boardWritetime");

    public final NumberPath<Integer> no = createNumber("no", Integer.class);

    public final StringPath userId = createString("userId");

    public final StringPath userNickname = createString("userNickname");

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

