package seontalk.control;

import java.util.List;

import seontalk.model.DeleteLogic;
import seontalk.model.InsertLogic;
import seontalk.model.SelectLogic;
import seontalk.model.UpdateLogic;

public class ConnectionCtrl {
	public final static String SELECT	= "select";
	public final static String INSERT	= "insert";
	public final static String UPDATE	= "update";
	public final static String DELETE	= "delete";
	public ConnectionCtrl() {
		
	}
	public Object Connect(String command, String work,Object pVO) {
		Object rVO = null;
		if(INSERT.equals(command)) {
			InsertLogic insLogic = new InsertLogic();
			rVO = insLogic.ConnectDao(work ,pVO);
		}
		else if(UPDATE.equals(command)) {
			UpdateLogic updLogic = new UpdateLogic();
			rVO = updLogic.ConnectDao(work ,pVO);
		}
		else if(DELETE.equals(command)) {
			DeleteLogic delLogic = new DeleteLogic();
			rVO = delLogic.ConnectDao(work ,pVO);
		}
		return rVO;
	}
	public List<Object> ConnectSelect(String command,String work,Object pVO){
		List<Object> VOList = null;
		if(SELECT.equals(command)) {
			SelectLogic selLogic = new SelectLogic();
			VOList = selLogic.ConnectDao(work, pVO);
		}
		return VOList;
	}
}
