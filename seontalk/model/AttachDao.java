package seontalk.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

import seontalk.util.DBConnectionMgr;
import seontalk.vo.AttachVO;

public class AttachDao {
	public final static String ALL			= "all";
	public final static String ATTACHING	= "attaching";
	
	Connection con;
	CallableStatement cstmt;
	PreparedStatement pstmt;
	ResultSet rs;
	public AttachVO Attaching(AttachVO pVO) {
		AttachVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_attaching(?,?,?)}");
			cstmt.setInt(1, pVO.getPost_num());
			cstmt.setString(2, pVO.getFiles());
			cstmt.setString(3, pVO.getBytes());
			rVO = new AttachVO();
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
	public AttachVO AttachDelete(AttachVO pVO) {
		AttachVO rVO = null;
		con = DBConnectionMgr.getConnection();
		try {
			cstmt = con.prepareCall("{call proc_attachDel(?,?)}");
			cstmt.setInt(1, pVO.getPost_num());
			cstmt.setString(2,pVO.getFiles());
			rVO = new AttachVO();
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
	public List<Object> Select(String work, AttachVO pVO) {
		List<Object> VOList = new Vector<>();
		con = DBConnectionMgr.getConnection();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT attach_num,post_num	  	");
			sql.append(",files,bytes			  		");
			sql.append("FROM attachment   					");
			sql.append("WHERE post_num = ?				    ");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, pVO.getPost_num());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				AttachVO rVO = new AttachVO();
				rVO.setAttach_num(rs.getInt("attach_num"));
				rVO.setPost_num(rs.getInt("post_num"));
				rVO.setFiles(rs.getString("files"));
				rVO.setBytes(rs.getString("bytes"));
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
