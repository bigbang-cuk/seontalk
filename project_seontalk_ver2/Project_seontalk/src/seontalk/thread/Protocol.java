package seontalk.thread;

public class Protocol {
	public static final int WAIT 			= 100;//대기상태
	public static final int ROOM_CREATE 	= 200;//방생성
	public static final int ROOM_IN 		= 210;//방입장
	public static final int ROOM_OUT 		= 220;//방퇴장
	public static final int MESSAGE 		= 300;//대화전송
	public static final int FILE 			= 310;//파일전송
	public static final int IMAGE 			= 320;//사진전송
	public static final int LOGIN 			= 400;//로그아웃
	public static final int LOGOUT 			= 410;//로그아웃
	public static final int EXIT 			= 500;//종료
	public static final int DB_SELECT		= 600;//DB조회
	public static final int DB_DML			= 610;//DB입력,수정,삭제
	public static String  seperator = "|";
}
