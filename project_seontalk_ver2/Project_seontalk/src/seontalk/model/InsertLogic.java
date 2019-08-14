package seontalk.model;

import seontalk.vo.AttachVO;
import seontalk.vo.ChatLogVO;
import seontalk.vo.ChatVO;
import seontalk.vo.MemberVO;
import seontalk.vo.PostVO;
import seontalk.vo.ReplyVO;

public class InsertLogic {

	public Object ConnectDao(String work,Object pVO) {
		Object rVO = null;
		if(pVO instanceof MemberVO) {
			MemberDao memDao = new MemberDao();
			MemberVO pVO2 = (MemberVO)pVO;
			if(MemberDao.SIGNUP.equals(work)) {
				rVO = memDao.SingUp(pVO2);
			}
			else if(MemberDao.FOLLOWING.equals(work)) {
				rVO = memDao.Following(pVO2);
			}
		}
		else if(pVO instanceof ChatVO) {
			ChatDao chatDao = new ChatDao();
			ChatVO pVO2 = (ChatVO)pVO;
			if(ChatDao.ROOM_CREATE.equals(work)) {
				rVO = chatDao.RoomCreate(pVO2);
			}
		}
		else if(pVO instanceof ChatLogVO) {
			ChatLogDao logDao = new ChatLogDao();
			ChatLogVO pVO2 = (ChatLogVO)pVO;
			if(ChatLogDao.TALKLOG_INS.equals(work)) {
				rVO = logDao.Insert(pVO2);
			}
		}
		else if(pVO instanceof PostVO) {
			PostDao postDao = new PostDao();
			PostVO pVO2 = (PostVO)pVO;
			if(PostDao.POST_INS.equals(work)) {
				rVO = postDao.PostIns(pVO2);
			}
		}
		else if(pVO instanceof ReplyVO) {
			ReplyDao replyDao = new ReplyDao();
			ReplyVO pVO2 = (ReplyVO)pVO;
			if(ReplyDao.REPLY_INS.equals(work)) {
				rVO = replyDao.ReplyIns(pVO2);
			}
		}
		else if(pVO instanceof AttachVO) {
			AttachDao attachDao = new AttachDao();
			AttachVO pVO2 = (AttachVO)pVO;
			if(AttachDao.ATTACHING.equals(work)) {
				rVO = attachDao.Attaching(pVO2);
			}
		}
		return rVO;
	}

}
