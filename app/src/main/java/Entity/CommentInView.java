package Entity;

import android.graphics.Bitmap;

/**
 * Created by 礼节 on 2016/10/28.
 */
public class CommentInView {
    private int commentId;//评论ID
    private String commentText;//内容
    private String commentTime;//评论时间 数据库类型时long

    //在数据库中不属于这个表  但需要显示
    private Bitmap userImg;
    private String userName;

    private int isParent;//是否是父章节

    public CommentInView(int commentId, String commentText, String commentTime, Bitmap userImg, String userName, int isParent) {
        this.commentId = commentId;
        this.commentText = commentText;
        this.commentTime = commentTime;
        this.userImg = userImg;
        this.userName = userName;
        this.isParent = isParent;
    }

    @Override
    public String toString() {
        return "CommentInView{" +
                "commentId=" + commentId +
                ", commentText='" + commentText + '\'' +
                ", commentTime='" + commentTime + '\'' +
                ", userImg=" + userImg +
                ", userName='" + userName + '\'' +
                ", isParent=" + isParent +
                '}';
    }

    public int getIsParent() {
        return isParent;
    }

    public void setIsParent(int isParent) {
        this.isParent = isParent;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public Bitmap getUserImg() {
        return userImg;
    }

    public void setUserImg(Bitmap userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
