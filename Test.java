public class Test {
	public static void main(String[] args) {
		int[]channels = new int[16];

		channels[0] = 1500;
		channels[1] = 1500;
		channels[2] = 1500;
		channels[3] = 1500;
		channels[4] = 1500;
		channels[5] = 1500;
		channels[6] = 1500;
		channels[7] = 1500;
		channels[8] = 1500;
		channels[9] = 1500;
		channels[10] = 1500;
		channels[11] = 1500;
		channels[12] = 1500;
		channels[13] = 1500;
		channels[14] = 1500;
		channels[15] = 1500;

		SBus sbus = new SBus();
		byte[] payload = sbus.encode(channels, (byte) 0x00);

		System.out.println(intArrayToBinaryString(channels));
		System.out.println(byteArrayToBinaryString(payload));

		System.out.println(intArrayToBinaryString(sbus.decode(payload)));
	}

	public static String intArrayToBinaryString(int[] channels) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < 15; i++) {
			sb.append("channel " + (i + 1) + " : ");
			sb.append(channels[i] + " -> 0x" + Integer.toHexString(channels[i]));
			sb.append("\r\n");
		}
		return sb.toString();
	}

	public static String byteArrayToBinaryString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; ++i) {
			sb.append("payload " + (i + 1) + " : ");
			sb.append(String.format("%02X ", b[i])); 
			sb.append(byteToBinaryString(b[i]));
			sb.append("\r\n");
		}
		return sb.toString();
	}

	public static String byteToBinaryString(byte n) {
		StringBuilder sb = new StringBuilder("00000000");
		for (int bit = 0; bit < 8; bit++) {
			if (((n >> bit) & 1) > 0) {
				sb.setCharAt(7 - bit, '1');
			}
		}
		return sb.toString();
	}
}
