package seontalk.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import seontalk.util.DBConnectionMgr;
import seontalk.vo.MemberVO;

public class MemberDao {
	public final static String ALL 				= "all";
	public final static String SIGNUP			= "signup";
	public final static String CHECK_ID 		= "check_id";
	public final static String CHECK_NICK   	= "check_nick";
	public final static String LOGIN	 	  	= "login";
	public final static String LOGOUT	 	  	= "logout";
	public final static String SEARCH   		= "search";
	public final static String MENTOR   		= "mentor";
	public final static String MENTOR_SEL  		= "mentor_sel";
	public final static String FOLLOWER   		= "follower";
	public final static String FOLLOWER_SEARCH	= "follower_search";
	public final static String FOLLOWING   		= "following";
	public final static String FOLLOWING_SEARCH = "following_search";
	public final static String UNFOLLOW   		= "unfollow";
	public final static String UPD_INFO   		= "upd_info";
	public final static String UPD_PW   		= "upd_pw";
	public final static String UPD_IMG   		= "upd_img";
	public final static String UPD_MSG   		= "upd_msg";
	Connection con;
	CallableStatement cstmt;
	PreparedStatement pstmt;
	ResultSet rs;
	public MemberVO SingUp(MemberVO pVO) {
		MemberVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_signup(?,?,?,?,?,?,?,?,?)}");
			cstmt.setString(1, pVO.getId());
			cstmt.setString(2, pVO.getName());
			cstmt.setString(3, pVO.getPw());
			cstmt.setString(4, pVO.getHp());
			cstmt.setString(5, pVO.getNick());
			cstmt.setString(6, pVO.getBirth());
			cstmt.setString(7, pVO.getMentoring());
			cstmt.setString(8, pVO.getInterest1());
			cstmt.setString(9, pVO.getInterest2());
			rVO = new MemberVO();
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
	public MemberVO Following(MemberVO pVO) {
		MemberVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_followIns(?,?)}");
			cstmt.setString(1, pVO.getId());
			cstmt.setString(2, pVO.getFollow_nick());
			rVO = new MemberVO();
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
	public MemberVO Login(MemberVO pVO) {
		MemberVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_login(?,?,?)}");
			cstmt.setString(1, pVO.getId());
			cstmt.setString(2, pVO.getPw());
			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			rVO = new MemberVO();
			rVO.setStatus(cstmt.executeUpdate());
			rVO.setKeyword(cstmt.getString(3));
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
	public MemberVO Logout(MemberVO pVO) {
		MemberVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_logout(?)}");
			cstmt.setString(1, pVO.getId());
			rVO = new MemberVO();
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
	public MemberVO UpdateInfo(MemberVO pVO) {
		MemberVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_memUpd(?,?,?,?,?,?,?)}");
			cstmt.setString(1, pVO.getId());
			cstmt.setString(2, pVO.getName());
			cstmt.setString(3, pVO.getHp());
			cstmt.setString(4, pVO.getNick());
			cstmt.setString(5, pVO.getBirth());
			cstmt.setString(6, pVO.getInterest1());
			cstmt.setString(7, pVO.getInterest2());
			rVO = new MemberVO();
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
	public MemberVO UpdateMsg(MemberVO pVO) {
		MemberVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_profileMsgUpd(?,?)}");
			cstmt.setString(1, pVO.getId());
			cstmt.setString(2, pVO.getProfile_msg());
			rVO = new MemberVO();
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
	public MemberVO UpdateImg(MemberVO pVO) {
		MemberVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_profileImgUpd(?,?)}");
			cstmt.setString(1, pVO.getId());
			cstmt.setString(2, pVO.getProfile_img());
			rVO = new MemberVO();
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
	public MemberVO UpdatePw(MemberVO pVO) {
		MemberVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_pwUpd(?,?)}");
			cstmt.setString(1, pVO.getId());
			cstmt.setString(2, pVO.getPw());
			rVO = new MemberVO();
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
	public MemberVO DeleteMem(MemberVO pVO) {
		MemberVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_memDel(?)}");
			cstmt.setString(1, pVO.getId());
			rVO = new MemberVO();
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
	public MemberVO DeleteFollow(MemberVO pVO) {
		MemberVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_followDel(?,?)}");
			cstmt.setString(1, pVO.getId());
			cstmt.setString(2, pVO.getFollow_nick());
			rVO = new MemberVO();
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

	public List<Object> Select(String work,MemberVO pVO) {
		List<Object> VOList = new Vector<>();
		con = DBConnectionMgr.getConnection();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT id,name,pw,hp,nick,birth  		");
			sql.append(",mentoring,interest1,interest2,rank_pt  ");
			sql.append(",profile_msg,profile_img,activation  	");
			sql.append(",font,font_size,theme,alarm,logon		");
			sql.append("FROM member NATURAL JOIN setting  		");
			if(ALL.equals(work)) {
				pstmt = con.prepareStatement(sql.toString());
			}
			else if(CHECK_ID.equals(work)) {
				sql.append("WHERE id = ?  ");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getId());
			}
			else if(CHECK_NICK.equals(work)) {
				sql.append("WHERE nick = ?  ");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getNick());
			}
			else if(SEARCH.equals(work)) {
				sql.append("WHERE mentoring = 'mentor'  				");
				sql.append("AND "+pVO.getColumn()+" like '%'|| ? ||'%'  ");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getKeyword());
			}
			else if(MENTOR.equals(work)) {
				sql.append("WHERE mentoring = 'mentor'  ");
				sql.append("ORDER BY rank_pt desc  		");
				pstmt = con.prepareStatement(sql.toString());
			}
			else if(MENTOR_SEL.equals(work)) {
				sql.append("WHERE mentoring = 'mentor'  ");
				sql.append("AND	  interest1 = ?  		");
				sql.append("OR 	  interest2 = ?  		");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getKeyword());
				pstmt.setString(2, pVO.getKeyword());
			}
			else if(FOLLOWER.equals(work)) {
				sql.append("WHERE nick in (SELECT follower_nick FROM follower WHERE id = ? )");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getId());
			}
			else if(FOLLOWER_SEARCH.equals(work)) {
				sql.append("WHERE nick in (SELECT follower_nick FROM follower WHERE id = ? )");
				sql.append("AND "+pVO.getColumn()+" like '%'|| ? ||'%'						");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getId());
				pstmt.setString(2, pVO.getKeyword());
			}
			else if(FOLLOWING.equals(work)) {
				sql.append("WHERE nick in (SELECT following_nick FROM follow WHERE id = ? )");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getId());
			}
			else if(FOLLOWING_SEARCH.equals(work)) {
				sql.append("WHERE nick in (SELECT following_nick FROM follow WHERE id = ? )");
				sql.append("AND "+pVO.getColumn()+" like '%'|| ? ||'%'					   ");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getId());
				pstmt.setString(2, pVO.getKeyword());
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				MemberVO rVO = new MemberVO();
				rVO.setId(rs.getString("id"));
				rVO.setName(rs.getString("name"));
				rVO.setPw(rs.getString("pw"));
				rVO.setHp(rs.getString("hp"));
				rVO.setNick(rs.getString("nick"));
				rVO.setBirth(rs.getString("birth"));
				rVO.setMentoring(rs.getString("mentoring"));
				rVO.setInterest1(rs.getString("interest1"));
				if(rs.getString("interest2")==null) {
					rVO.setInterest2("");
				}
				else {
					rVO.setInterest2(rs.getString("interest2"));
				}
				rVO.setRank_pt(rs.getInt("rank_pt"));
				rVO.setProfile_msg(rs.getString("profile_msg"));
				rVO.setProfile_img(rs.getString("profile_img"));
				rVO.setActivation(rs.getInt("activation"));
				rVO.setFont(rs.getString("font"));
				rVO.setFont_size(rs.getInt("font_size"));
				rVO.setTheme(rs.getString("theme"));
				rVO.setAlarm(rs.getString("alarm"));
				rVO.setLogon(rs.getInt("logon"));
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
