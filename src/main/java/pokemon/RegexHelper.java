package pokemon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.cj.jdbc.exceptions.PacketTooBigException;

public class RegexHelper {
	public static boolean checkAlphaNumericInput(String str) {
		Pattern pattern=Pattern.compile("^[A-Za-z0-9]{1,}$",Pattern.CASE_INSENSITIVE);
		Matcher matcher=pattern.matcher(str);
		return matcher.find();
	}
	public static boolean checkChatInput(String str) {
		Pattern pattern=Pattern.compile(":",Pattern.CASE_INSENSITIVE);
		Matcher matcher=pattern.matcher(str);
		return !matcher.find();
	}

}
