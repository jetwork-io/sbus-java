import java.util.ArrayList;

public class SBus {

	int SBUS_ERR_INVALID_ARG = -5;
	
	//servo channels -> sbus payload : encode
	public byte[] encode(int[] channels,byte opt) {
		byte[] packet = new byte[25];
		byte SBUS_HEADER = 0x0f;
		byte SBUS_END = 0x00;
		if(channels == null) {
		    return packet;
		}
		
		packet[0] = SBUS_HEADER;
		packet[23] = opt;
 		packet[24] = SBUS_END;
  		
 	    packet[1] = (byte) (channels[0] & 0xff);
 	    packet[2] = (byte) (channels[0] >> 8 & 0b111);
 	    int currentByte = 2;
 	    int usedBits = 3; // from LSB
		
 		 for (int ch = 1; ch < 16; ch++)
 	    {
 	        // while channel not fully encoded
 	        for (int bitsWritten = 0; bitsWritten < 11;)
 	        {
 	            // strip written bits, shift over used bits
 	            packet[currentByte] |= channels[ch] >> bitsWritten << usedBits & 0xff;

 	            int hadToWrite = 11 - bitsWritten;
 	            int couldWrite = 8 - usedBits;

 	            int wrote = couldWrite;
 	            if (hadToWrite < couldWrite)
 	            {
 	                wrote = hadToWrite;
 	            }
 	            else
 	            {
 	                currentByte++;
 	            }

 	            bitsWritten += wrote;
 	            usedBits += wrote;
 	            usedBits %= 8;
 	        }
 	    }
		
		return packet;
	}
	
	//sbus payload -> servo channels : decode
	public int[] decode(byte[] packet) {
		
		int[] channels = new int[16];
		
		int[] payload = new int[23];

		for(int i = 0; i < 23 ; i++) {
			payload[i] =  packet[i+1];
		}
		
		channels[0]  = (payload[0] & 0xFF) | (payload[1] << 8 & 0x0700);
	    channels[1]  = (payload[1] >> 3  & 0x1F) | (payload[2] << 5 & 0x7E0);
	    channels[2]  = (payload[2] >> 6 & 0x03) | (payload[3] << 2 & 0x3FC) | (payload[4] << 10 & 0x400);
	    channels[3]  = (payload[4] >> 1 & 0x7F) | (payload[5] << 7 & 0x780);
	    channels[4]  = (payload[5] >> 4 & 0x0F) | (payload[6] << 4 & 0x7F0);
        channels[5]  = (payload[6] >> 7 & 0x01) | (payload[7] << 1 & 0x1FE) | (payload[8] << 9 & 0x600);
        channels[6]  = (payload[8] >> 2 & 0x3F) | (payload[9] << 6 & 0x7C0);
        channels[7]  = (payload[9] >> 5 & 0x1F) | (payload[10] << 3 & 0x7F8);
        
	    channels[8]  = (payload[11]  & 0xFF) | (payload[12] << 8 & 0x0700);
	    channels[9]  = (payload[12] >> 3 & 0x1F) | (payload[13] << 5 & 0x7E0);
	    channels[10] = (payload[13] >> 6 & 0x03) | (payload[14] << 2 & 0x3FC) | (payload[15] << 10& 0x400);
	    channels[11] = (payload[15] >> 1 & 0x7F) | (payload[16] << 7 & 0x780);
	    channels[12] = (payload[16] >> 4 & 0x0F) | (payload[17] << 4 & 0x7F0);
	    channels[13] = (payload[17] >> 7 & 0x01) | (payload[18] << 1 & 0x1FE) | (payload[19] << 9  & 0x600);
	    channels[14] = (payload[19] >> 2 & 0x3F) | (payload[20] << 6 & 0x7C0);
	    channels[15] = (payload[20] >> 5 & 0x1F) | (payload[21] << 3 & 0x7F8);
		
	    return channels;
	}
}
