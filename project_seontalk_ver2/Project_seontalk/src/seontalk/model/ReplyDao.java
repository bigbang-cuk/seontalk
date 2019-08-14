package seontalk.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import seontalk.util.DBConnectionMgr;
import seontalk.vo.ReplyVO;

public class ReplyDao {
	public final static String ALL				= "all";
	public final static String REPLY_INS		= "reply_ins";
	public final static String REPLY_UPD		= "reply_upd";
	public final static String REPLY_DEL		= "reply_del";
	public final static String REPLY_UP			= "reply_up";
	public final static String REPLY_DOWN		= "reply_down";
	Connection con;
	CallableStatement cstmt;
	PreparedStatement pstmt;
	ResultSet rs;
	public ReplyVO ReplyIns(ReplyVO pVO) {
		ReplyVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_replyIns(?,?,?)}");
			cstmt.setInt(1, pVO.getPost_num());
			cstmt.setString(2, pVO.getReply_nick());
			cstmt.setString(3, pVO.getReply_content());
			rVO = new ReplyVO();
			rVO.setStatus(cstmt.executeUpdate());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				cstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return rVO;
	}
	public ReplyVO ReplyUpd(ReplyVO pVO) {
		ReplyVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_replyUpd(?,?,?)}");
			cstmt.setInt(1, pVO.getPost_num());
			cstmt.setInt(2, pVO.getReply_num());
			cstmt.setString(3, pVO.getReply_content());
			rVO = new ReplyVO();
			rVO.setStatus(cstmt.executeUpdate());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				cstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return rVO;
	}
	public ReplyVO ReplyUp(ReplyVO pVO) {
		ReplyVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_replyUp(?,?,?,?)}");
			cstmt.setString(1, pVO.getReply_id());
			cstmt.setInt(2, pVO.getReply_num());
			cstmt.setString(3, pVO.getKeyword());
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			rVO = new ReplyVO();
			rVO.setStatus(cstmt.executeUpdate());
			rVO.setKeyword(cstmt.getString(4));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				cstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return rVO;
	}
	public ReplyVO ReplyDown(ReplyVO pVO) {
		ReplyVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_replyDown(?,?,?,?)}");
			cstmt.setString(1, pVO.getReply_id());
			cstmt.setInt(2, pVO.getReply_num());
			cstmt.setString(3, pVO.getKeyword());
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			rVO = new ReplyVO();
			rVO.setStatus(cstmt.executeUpdate());
			rVO.setKeyword(cstmt.getString(4));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				cstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return rVO;
	}
	public ReplyVO ReplyDel(ReplyVO pVO) {
		ReplyVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_replyDel(?,?)}");
			cstmt.setInt(1, pVO.getPost_num());
			cstmt.setInt(2, pVO.getReply_num());
			rVO = new ReplyVO();
			rVO.setStatus(cstmt.executeUpdate());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				cstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return rVO;
	}

	public List<Object> Select(String work, ReplyVO pVO) {
		List<Object> VOList = new Vector<>();
		con = DBConnectionMgr.getConnection();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT post_num,reply_num,reply_nick  	");
			sql.append(",reply_content,activation  				");
			sql.append(",recommend_cnt,decommend_cnt  			");
			sql.append(",reply_date,reply_time   				");
			sql.append("FROM reply   							");
			sql.append("WHERE post_num = ?						");
			sql.append("ORDER BY reply_date,reply_time ASC   ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, pVO.getPost_num());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ReplyVO rVO = new ReplyVO();
				rVO.setPost_num(rs.getInt("post_num"));
				rVO.setReply_num(rs.getInt("reply_num"));
				rVO.setReply_nick(rs.getString("reply_nick"));
				rVO.setReply_content(rs.getString("reply_content"));
				rVO.setActivation(rs.getInt("activation"));
				rVO.setRecommend_cnt(rs.getInt("recommend_cnt"));
				rVO.setDecommend_cnt(rs.getInt("decommend_cnt"));
				rVO.setReply_date(rs.getString("reply_date"));
				rVO.setReply_time(rs.getString("reply_time"));
				VOList.add(rVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				pstmt.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return VOList;
	}

}
