package seontalk.model;

import seontalk.vo.AttachVO;
import seontalk.vo.ChatLogVO;
import seontalk.vo.ChatVO;
import seontalk.vo.MemberVO;
import seontalk.vo.PostVO;
import seontalk.vo.ReplyVO;

public class UpdateLogic {

	public Object ConnectDao(String work,Object pVO) {
		Object rVO = null;
		if(pVO instanceof MemberVO) {
			MemberDao memDao = new MemberDao();
			MemberVO pVO2 = (MemberVO)pVO;
			if(MemberDao.LOGIN.equals(work)) {
				rVO = memDao.Login(pVO2);
			}
			else if(MemberDao.LOGOUT.equals(work)) {
				rVO = memDao.Logout(pVO2);
			}
			else if(MemberDao.UPD_INFO.equals(work)) {
				rVO = memDao.UpdateInfo(pVO2);
			}
			else if(MemberDao.UPD_PW.equals(work)) {
				rVO = memDao.UpdatePw(pVO2);
			}
			else if(MemberDao.UPD_MSG.equals(work)) {
				rVO = memDao.UpdateMsg(pVO2);
			}
			else if(MemberDao.UPD_IMG.equals(work)) {
				rVO = memDao.UpdateImg(pVO2);
			}
		}
		else if(pVO instanceof ChatVO) {
			ChatDao chatDao = new ChatDao();
			ChatVO pVO2 = (ChatVO)pVO;
			if(ChatDao.READING.equals(work)) {
				rVO = chatDao.Reading(pVO2);
			}
			else if(ChatDao.UNREADING.equals(work)) {
				rVO = chatDao.UnReading(pVO2);
			}
		}
		else if(pVO instanceof ChatLogVO) {
			ChatLogDao logDao = new ChatLogDao();
			ChatLogVO pVO2 = (ChatLogVO)pVO;
			rVO = logDao.Update(pVO2);
		}
		else if(pVO instanceof PostVO) {
			PostDao postDao = new PostDao();
			PostVO pVO2 = (PostVO)pVO;
			if(PostDao.POST_UPD.equals(work)) {
				rVO = postDao.PostUpd(pVO2);
			}
			else if(PostDao.POST_VIEW.equals(work)) {
				rVO = postDao.PostView(pVO2);
			}
			else if(PostDao.POST_UP.equals(work)) {
				rVO = postDao.PostUp(pVO2);
			}
			else if(PostDao.POST_DOWN.equals(work)) {
				rVO = postDao.PostDown(pVO2);
			}
		}
		else if(pVO instanceof ReplyVO) {
			ReplyDao replyDao = new ReplyDao();
			ReplyVO pVO2 = (ReplyVO)pVO;
			if(ReplyDao.REPLY_UPD.equals(work)) {
				rVO = replyDao.ReplyUpd(pVO2);
			}
			else if(ReplyDao.REPLY_UP.equals(work)) {
				rVO = replyDao.ReplyUp(pVO2);
			}
			else if(ReplyDao.REPLY_DOWN.equals(work)) {
				rVO = replyDao.ReplyDown(pVO2);
			}
		}
		else if(pVO instanceof AttachVO) {
			AttachDao attachDao = new AttachDao();
			AttachVO pVO2 = (AttachVO)pVO;
			if(AttachDao.ALL.equals(work)) {
				//rVO = attachDao.ReplyIns(pVO2);
			}
		}
		return rVO;
	}

}
