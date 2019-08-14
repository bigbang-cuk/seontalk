package seontalk.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import seontalk.util.DBConnectionMgr;
import seontalk.vo.ChatLogVO;
import seontalk.vo.ChatVO;

public class ChatLogDao {
	public final static String ALL					= "all";
	public final static String TALKLOG_INS			= "talklog_ins";
	public final static String TALKLOG_UPD			= "talklog_upd";
	
	Connection con;
	CallableStatement cstmt;
	PreparedStatement pstmt;
	ResultSet rs;
	public ChatLogVO Insert(ChatLogVO pVO) {
		ChatLogVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_talklogIns(?,?,?)}");
			cstmt.setInt(1, pVO.getTalklist_num());
			cstmt.setString(2, pVO.getWrite_nick());
			cstmt.setString(3, pVO.getContent());
			rVO = new ChatLogVO();
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

	public ChatLogVO Update(ChatLogVO pVO) {
		// TODO Auto-generated method stub
		return null;
	}

	public ChatLogVO Delete(ChatLogVO pVO) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> Select(String work, ChatLogVO pVO) {
		List<Object> VOList = new Vector<>();
		con = DBConnectionMgr.getConnection();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT talklist_num,log_num	  	");
			sql.append(",log_date,log_time,content 		");
			sql.append(",confirm,write_nick		  		");
			sql.append("FROM talklog	   				");
			if(ALL.equals(work)) {
				sql.append("WHERE talklist_num = ?			");
				sql.append("ORDER BY log_date,log_time		");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, pVO.getTalklist_num());
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ChatLogVO rVO = new ChatLogVO();
				rVO.setTalklist_num(rs.getInt("talklist_num"));
				rVO.setLog_num(rs.getInt("log_num"));
				rVO.setLog_date(rs.getString("log_date"));
				rVO.setLog_time(rs.getString("log_time"));
				rVO.setContent(rs.getString("content"));
				rVO.setConfirm(rs.getInt("confirm"));
				rVO.setWrite_nick(rs.getString("write_nick"));
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
