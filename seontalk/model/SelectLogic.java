package seontalk.model;

import java.util.List;
import java.util.Vector;

import seontalk.vo.AttachVO;
import seontalk.vo.ChatLogVO;
import seontalk.vo.ChatVO;
import seontalk.vo.MemberVO;
import seontalk.vo.PostVO;
import seontalk.vo.ReplyVO;

public class SelectLogic {
	public List<Object> ConnectDao(String work,Object pVO) {
		List<Object> VOList = null;
		if(pVO instanceof MemberVO) {
			MemberVO pVO2 = (MemberVO)pVO;
			MemberDao memDao = new MemberDao();
			VOList = memDao.Select(work,pVO2);
		}
		else if(pVO instanceof ChatVO) {
			ChatVO pVO2 = (ChatVO)pVO;
			ChatDao chatDao = new ChatDao();
			VOList = chatDao.Select(work,pVO2);
		}
		else if(pVO instanceof ChatLogVO) {
			ChatLogVO pVO2 = (ChatLogVO)pVO;
			ChatLogDao logDao = new ChatLogDao();
			VOList = logDao.Select(work,pVO2);
		}
		else if(pVO instanceof PostVO) {
			PostVO pVO2 = (PostVO)pVO;
			PostDao postDao = new PostDao();
			VOList = postDao.Select(work,pVO2);
		}
		else if(pVO instanceof ReplyVO) {
			ReplyVO pVO2 = (ReplyVO)pVO;
			ReplyDao replyDao = new ReplyDao();
			VOList = replyDao.Select(work,pVO2);
		}
		else if(pVO instanceof AttachVO) {
			AttachVO pVO2 = (AttachVO)pVO;
			AttachDao attachDao = new AttachDao();
			VOList = attachDao.Select(work,pVO2);
		}
		return VOList;
	}
}
