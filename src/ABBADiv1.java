
public class ABBADiv1 {

	public String canObtain(String initial, String target) {
		int startPointer = 0;
		int endPointer = target.length() - 1;
		boolean forward = true;
		while ((endPointer - startPointer + 1) > initial.length()) {

			switch ((forward) ? 1 : 0) {
			case 1:
				if (target.charAt(startPointer) != 'A') {
					forward = false;
				}

				startPointer++;
				break;

			case 0:
				if (target.charAt(endPointer) != 'A') {
					forward = true;
				}
				endPointer--;
				break;
			}
		}
		String targetStr;
		if (forward) {
			targetStr = target.substring(startPointer, endPointer + 1);
		} else {
			targetStr = new StringBuilder(target.substring(startPointer, endPointer + 1)).reverse().toString();
		}

		if (targetStr.equals(initial)) {
			return "Possible";
		} else {
			return "Impossible";
		}
	}

}
