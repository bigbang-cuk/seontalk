package seontalk.model;

import seontalk.vo.AttachVO;
import seontalk.vo.ChatLogVO;
import seontalk.vo.ChatVO;
import seontalk.vo.MemberVO;
import seontalk.vo.PostVO;
import seontalk.vo.ReplyVO;

public class DeleteLogic {

	public Object ConnectDao(String work,Object pVO) {
		Object rVO = 0;
		if(pVO instanceof MemberVO) {
			MemberDao memDao = new MemberDao();
			MemberVO pVO2 = (MemberVO)pVO;
			if(MemberDao.ALL.equals(work)) {
				rVO = memDao.DeleteMem(pVO2);
			}
			else if(MemberDao.UNFOLLOW.equals(work)) {
				rVO = memDao.DeleteFollow(pVO2);
			}
		}
		else if(pVO instanceof ChatVO) {
			ChatDao chatDao = new ChatDao();
			ChatVO pVO2 = (ChatVO)pVO;
			if(ChatDao.ROOM_OUT.equals(work)) {
				rVO = chatDao.RoomOut(pVO2);
			}
		}
		else if(pVO instanceof ChatLogVO) {
			ChatLogDao logDao = new ChatLogDao();
			ChatLogVO pVO2 = (ChatLogVO)pVO;
			rVO = logDao.Delete(pVO2);
		}
		else if(pVO instanceof PostVO) {
			PostDao postDao = new PostDao();
			PostVO pVO2 = (PostVO)pVO;
			if(PostDao.POST_DEL.equals(work)) {
				rVO = postDao.PostDel(pVO2);
			}
		}
		else if(pVO instanceof ReplyVO) {
			ReplyDao replyDao = new ReplyDao();
			ReplyVO pVO2 = (ReplyVO)pVO;
			if(ReplyDao.REPLY_DEL.equals(work)) {
				rVO = replyDao.ReplyDel(pVO2);
			}
		}
		else if(pVO instanceof AttachVO) {
			AttachDao attachDao = new AttachDao();
			AttachVO pVO2 = (AttachVO)pVO;
			if(AttachDao.ATTACHING.equals(work)) {
				rVO = attachDao.AttachDelete(pVO2);
			}
		}
		return rVO;
	}

}
