import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class ParenthesesAndPermutation {
	public String getSequence(int[] p) {
		List<String> parantesisSequences = getParenthesis(p.length / 2);

		for (String s : parantesisSequences) {
			String t = getTfromS(s, p);
			if (isCorrectSequence(t)) {
				return s;
			}

		}
		return "Impossible";
	}

	private String getTfromS(String s, int[] p) {
		char[] t = new char[s.length()];
		for (int index = 0; index < p.length; index++) {
			t[p[index]] = s.charAt(index);
		}
		return new String(t);
	}

	private List<String> getParenthesis(int n) {
		if (n > 0) {
			List<String> parantesisSequences = new ArrayList<String>();
			getParanthesisCombinations(n, 0, "", parantesisSequences);
			return parantesisSequences;
		} else {
			return Collections.singletonList("");
		}

	}

	private void getParanthesisCombinations(int openStock, int closeStock, String s, List<String> sequences) {
		if (openStock == 0 && closeStock == 0) {
			sequences.add(s);
		}
		if (openStock > 0) {
			getParanthesisCombinations(openStock - 1, closeStock + 1, s + "(", sequences);
		}
		if (closeStock > 0) {
			getParanthesisCombinations(openStock, closeStock - 1, s + ")", sequences);
		}
	}

	private boolean isCorrectSequence(String sequence) {
		int splitNumber = 0;
		if (sequence == "") {
			return true;
		}
		int splitIndex = sequence.indexOf(")", splitNumber++);
		if (isCorrectSequence(sequence.substring(0, splitIndex))
				&& isCorrectSequence(sequence.substring(splitIndex + 1))) {
			return true;
		} else if (Pattern.compile("^\\(.*?\\)$").matcher(sequence).find()) {
			if (isCorrectSequence(sequence.substring(1, sequence.length() - 1))) {
				return true;
			}
		}
		return false;
	}

}