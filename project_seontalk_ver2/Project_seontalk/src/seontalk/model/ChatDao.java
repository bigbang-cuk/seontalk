package seontalk.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import seontalk.util.DBConnectionMgr;
import seontalk.vo.ChatVO;

public class ChatDao {
	public final static String ALL				= "all";
	public final static String SELECT			= "select";
	public final static String ROOM_CREATE		= "room_create";
	public final static String READING			= "reading";
	public final static String UNREADING		= "unreading";
	public final static String ROOM_OUT			= "room_out";
	
	Connection con;
	CallableStatement cstmt;
	PreparedStatement pstmt;
	ResultSet rs;
	public ChatVO RoomCreate(ChatVO pVO) {
		ChatVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_talklistIns(?,?)}");
			cstmt.setString(1, pVO.getNick());
			cstmt.setString(2, pVO.getPartner_nick());
			rVO = new ChatVO();
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
	public ChatVO Reading(ChatVO pVO) {
		ChatVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_talkReading(?,?)}");
			cstmt.setInt(1, pVO.getTalklist_num());
			cstmt.setString(2, pVO.getNick());
			rVO = new ChatVO();
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
	public ChatVO UnReading(ChatVO pVO) {
		ChatVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_talkUnReading(?,?)}");
			cstmt.setInt(1, pVO.getTalklist_num());
			cstmt.setString(2, pVO.getNick());
			rVO = new ChatVO();
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

	public ChatVO RoomOut(ChatVO pVO) {
		ChatVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_talklistOut(?,?)}");
			cstmt.setInt(1, pVO.getTalklist_num());
			cstmt.setString(2, pVO.getNick());
			rVO = new ChatVO();
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

	public List<Object> Select(String work, ChatVO pVO) {
		List<Object> VOList = new Vector<>();
		con = DBConnectionMgr.getConnection();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT talklist_num,open_date	  	");
			sql.append(",nick,my_inout,my_reading	  		");
			sql.append(",partner_nick,partner_inout  		");
			sql.append(",partner_reading   					");
			sql.append("FROM talklist   					");
			if(ALL.equals(work)) {
				sql.append("WHERE (nick = ?					    ");
				sql.append("AND my_inout = 'in')			    ");
				sql.append("OR (partner_nick = ?			    ");
				sql.append("AND partner_inout = 'in')		    ");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getNick());
				pstmt.setString(2, pVO.getNick());
			}
			else if(ROOM_CREATE.equals(work)){
				sql.append("WHERE nick = ?					    ");
				sql.append("AND partner_nick = ?			    ");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getNick());
				pstmt.setString(2, pVO.getPartner_nick());
			}
			else if(SELECT.contentEquals(work)) {
				sql.append("WHERE talklist_num = ?			    ");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, pVO.getTalklist_num());
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ChatVO rVO = new ChatVO();
				rVO.setTalklist_num(rs.getInt("talklist_num"));
				rVO.setOpen_date(rs.getString("open_date"));
				rVO.setNick(rs.getString("nick"));
				rVO.setMy_inout(rs.getString("my_inout"));
				rVO.setMy_reading(rs.getInt("my_reading"));
				rVO.setPartner_nick(rs.getString("partner_nick"));
				rVO.setPartner_inout(rs.getString("partner_inout"));
				rVO.setPartner_reading(rs.getInt("partner_reading"));
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
