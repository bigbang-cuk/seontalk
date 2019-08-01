package seontalk.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import seontalk.util.DBConnectionMgr;
import seontalk.vo.PostVO;

public class PostDao {
	public final static String POST_INS			= "post_ins";
	public final static String POST_UPD			= "post_upd";
	public final static String POST_DEL			= "post_del";
	public final static String ALL				= "all";
	public final static String SEARCH			= "search";
	public final static String POST_SEL			= "post_sel";
	public final static String POST_MY			= "post_my";
	public final static String POST_BEST		= "post_best";
	public final static String POST_UP			= "post_up";
	public final static String POST_DOWN		= "post_down";
	public final static String POST_VIEW		= "post_view";
	Connection con;
	CallableStatement cstmt;
	PreparedStatement pstmt;
	ResultSet rs;
	public PostVO PostIns(PostVO pVO) {
		PostVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_postIns(?,?,?,?,?,?)}");
			cstmt.setString(1, pVO.getId());
			cstmt.setString(2, pVO.getNick());
			cstmt.setString(3, pVO.getPost_title());
			cstmt.setString(4, pVO.getPost_content());
			cstmt.setString(5, pVO.getInterest());
			cstmt.registerOutParameter(6, java.sql.Types.INTEGER);
			rVO = new PostVO();
			rVO.setStatus(cstmt.executeUpdate());
			rVO.setPost_num(cstmt.getInt(6));
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

	public PostVO PostUpd(PostVO pVO) {
		PostVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_postUpd(?,?,?,?)}");
			cstmt.setInt(1, pVO.getPost_num());
			cstmt.setString(2, pVO.getPost_title());
			cstmt.setString(3, pVO.getPost_content());
			cstmt.setString(4, pVO.getInterest());
			rVO = new PostVO();
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
	public PostVO PostView(PostVO pVO) {
		PostVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_PostView(?,?,?)}");
			cstmt.setString(1, pVO.getId());
			cstmt.setInt(2, pVO.getPost_num());
			cstmt.setString(3, pVO.getKeyword());
			rVO = new PostVO();
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
	public PostVO PostUp(PostVO pVO) {
		PostVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_PostUp(?,?,?,?)}");
			cstmt.setString(1, pVO.getId());
			cstmt.setInt(2, pVO.getPost_num());
			cstmt.setString(3, pVO.getKeyword());
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			rVO = new PostVO();
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
	public PostVO PostDown(PostVO pVO) {
		PostVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_PostDown(?,?,?,?)}");
			cstmt.setString(1, pVO.getId());
			cstmt.setInt(2, pVO.getPost_num());
			cstmt.setString(3, pVO.getKeyword());
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			rVO = new PostVO();
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

	public PostVO PostDel(PostVO pVO) {
		PostVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_postDel(?)}");
			cstmt.setInt(1, pVO.getPost_num());
			rVO = new PostVO();
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

	public List<Object> Select(String work, PostVO pVO) {
		List<Object> VOList = new Vector<>();
		con = DBConnectionMgr.getConnection();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT post_num,post_title,post_content  	");
			sql.append(",id,nick,post_date,post_time,interest  		");
			sql.append(",view_cnt,recommend_cnt,decommend_cnt  		");
			sql.append(",reply_cnt,activation   					");
			sql.append("FROM post   								");
			if(ALL.equals(work)) {
				sql.append("ORDER BY post_date DESC,post_time DESC   ");
				pstmt = con.prepareStatement(sql.toString());
			}
			else if(POST_MY.equals(work)) {
				sql.append("WHERE id = ?  							 ");
				sql.append("ORDER BY post_date DESC,post_time DESC   ");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getId());
			}
			else if(POST_BEST.equals(work)) {
				sql.append("WHERE interest in (?,?)					 ");
				sql.append("AND recommend_cnt >= 10					 ");
				sql.append("AND decommend_cnt < recommend_cnt/2		 ");
				sql.append("ORDER BY post_date DESC,post_time DESC   ");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getInterest());
				pstmt.setString(2, pVO.getKeyword());
			}
			else if(POST_SEL.equals(work)) {
				sql.append("WHERE interest = ?  					 ");
				sql.append("ORDER BY post_date DESC,post_time DESC   ");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getInterest());
			}
			else if(SEARCH.equals(work)) {
				sql.append("WHERE interest = ?  						");
				sql.append("AND "+pVO.getColumn()+" like '%'|| ? ||'%'  ");
				sql.append("ORDER BY post_date DESC,post_time DESC   	");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, pVO.getInterest());
				pstmt.setString(2, pVO.getKeyword());
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				PostVO rVO = new PostVO();
				rVO.setPost_num(rs.getInt("post_num"));
				rVO.setView_cnt(rs.getInt("view_cnt"));
				rVO.setRecommend_cnt(rs.getInt("recommend_cnt"));
				rVO.setDecommend_cnt(rs.getInt("decommend_cnt"));
				rVO.setReply_cnt(rs.getInt("reply_cnt"));
				rVO.setId(rs.getString("id"));
				rVO.setNick(rs.getString("nick"));
				rVO.setPost_title(rs.getString("post_title"));
				rVO.setPost_content(rs.getString("post_content"));
				rVO.setPost_date(rs.getString("post_date"));
				rVO.setPost_time(rs.getString("post_time"));
				rVO.setInterest(rs.getString("interest"));
				rVO.setActivation(rs.getInt("activation"));
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
