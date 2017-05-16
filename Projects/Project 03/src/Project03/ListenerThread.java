package Project03;

import java.io.*;
import java.net.Socket;

public class ListenerThread extends Thread
{
    public volatile static boolean endThread = false;
    private Socket socket = null;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ListenerThread(Socket socket)
    {
        super("Listener Thread");
        this.socket = socket;
    }

    /**
     * The overridden run() function belonging to the Thread class.
     * This is what handles the communication between the server and the client.
     */
    public void run()
    {
        try
        {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            ipv4();

            //respondToServer(checksum, 1);
            Ipv4Client.disconnectFromServer();
        }
        catch (IOException e) {
            System.err.println("ERROR: Connection lost with server.");
        }
    }

    /**
     * Calculates the Internet Checksum from a given array of data.
     * The algorithm maintains a 32-bit number as the sum and goes through
     * the array two bytes at a time, forms a 16-bit number out of each pair
     * of bytes and adds it to the sum. After each time it adds,
     * it checks for overflow. If overflow occurs, it is cleared and
     * added back in to the sum (acting like a wrap-around). Finally,
     * when the sum is calculated we perform one's complement and return
     * the rightmost 16 bits of the sum.
     * @param data - The array of bytes to calculate the Internet Checksum from.
     * @return - Returns the Internet Checksum.
     */
    private static short internetChecksum(byte[] data)
    {
        int sum = 0;
        int firstByte;
        byte secondByte;
        boolean allPairs = (data.length % 2 == 0);

        for(int i = 0; i < data.length; i += 2)
        {
            firstByte = data[i];
            // If the last byte doesn't have a pair.
            if(!allPairs && (i == data.length - 1))
                secondByte = 0;
            else
                secondByte = data[i + 1];

            // Forms a 16-bit number from two consecutive bytes
            // and adds it to the sum.
            sum += ((firstByte << 8 & 0xFF00) | (secondByte & 0xFF));

            // Checks for overflow on the sum.
            if((sum & 0xFFFF0000) > 0)
            {
                sum &= 0xFFFF;
                sum++;
            }
        }

        // Get one's complement and return the 16 rightmost bits.
        return (short)~(sum & 0xFFFF);
    }

    /**
     * Responds to the server with the 2 byte sequence
     * obtained from the given Internet Checksum.
     * @param checksum - The Internet Checksum to be sent to the server.
     */
    private void respondToServer(short checksum, int sequenceSize)
    {
        try
        {
            for(int i = sequenceSize - 1; i >= 0; i--)
                outputStream.write(checksum >> (8 * i));

            if(inputStream.read() == 1)
                System.out.println("Response good.");
            else
                System.out.println("Response bad.");
        }
        catch (IOException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    private byte[] ipv4()
    {
        /*
            0                   1                   2                   3
            0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
           +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
           |Version|  IHL  |Type of Service|          Total Length         |
           +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
           |         Identification        |Flags|      Fragment Offset    |
           +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
           |  Time to Live |    Protocol   |         Header Checksum       |
           +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
           |                       Source Address                          |
           +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
           |                    Destination Address                        |
           +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
           |                    Options                    |    Padding    |
           +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

                            Example Internet Datagram Header
         */
        byte[] ipv4 = new byte[32];


        int length1stByte = 0;
        int length2ndByte = 0;


        /*
            1st Byte
            Version:  4 bits
                The Version field indicates the format of the internet header.  This
                document describes version 4.

            IHL (or HLen):  4 bits
                Internet Header Length is the length of the internet header in 32
                bit words, and thus points to the beginning of the data.  Note that
                the minimum value for a correct header is 5.
         */
        int VERSION = 4;
        int hLen = 15;
        ipv4[0] = (byte)((VERSION << 4 & 0xF0) | (hLen & 0xF));

        /*
            2nd Byte
            Type of Service (or TOS):  8 bits
                The Type of Service provides an indication of the abstract
                parameters of the quality of service desired.  These parameters are
                to be used to guide the selection of the actual service parameters
                when transmitting a datagram through a particular network.  Several
                networks offer service precedence, which somehow treats high
                precedence traffic as more important than other traffic (generally
                by accepting only traffic above a certain precedence at time of high
                load).  The major choice is a three way tradeoff between low-delay,
                high-reliability, and high-throughput.

            Project Specification:
                TOS - Do not implement.
         */
        int TOS = 0;
        ipv4[1] = (byte) TOS;

        /*
            3rd and 4th Bytes
            Total Length (or Length):  16 bits
                Total Length is the length of the datagram, measured in octets,
                including internet header and data.  This field allows the length of
                a datagram to be up to 65,535 octets.  Such long datagrams are
                impractical for most hosts and networks.  All hosts must be prepared
                to accept datagrams of up to 576 octets (whether they arrive whole
                or in fragments).  It is recommended that hosts only send datagrams
                larger than 576 octets if they have assurance that the destination
                is prepared to accept the larger datagrams.

                The number 576 is selected to allow a reasonable sized data block to
                be transmitted in addition to the required header information.  For
                example, this size allows a data block of 512 octets plus 64 header
                octets to fit in a datagram.  The maximal internet header is 60
                octets, and a typical internet header is 20 octets, allowing a
                margin for headers of higher level protocols.
         */
//        packet = length << 16;
//        ipv4[2] = (byte) packet;
//        ipv4[3] = (byte) 0;

        /*
            5th and 6th Bytes
            Identification (or Ident):  16 bits
                An identifying value assigned by the sender to aid in assembling the
                fragments of a datagram.

            Project Specification:
                Ident - Do not implement.
         */
        int ident = 0;
        ipv4[4] = (byte) ident;
        ipv4[5] = (byte) ident;

        /*
            7th Byte
            Flags:  3 bits
                Various Control Flags.
                  Bit 0: reserved, must be zero
                  Bit 1: (DF) 0 = May Fragment,  1 = Don't Fragment.
                  Bit 2: (MF) 0 = Last Fragment, 1 = More Fragments.

                      0   1   2
                    +---+---+---+
                    |   | D | M |
                    | 0 | F | F |
                    +---+---+---+

            Fragment Offset:  13 bits
                This field indicates where in the datagram this fragment belongs.
                The fragment offset is measured in units of 8 octets (64 bits).  The
                    first fragment has offset zero.

           Project Specification:
                Flags - Implement assuming no fragmentation so 010.
                Offset - Do not implement.
         */
        int flags = 0b010;
        int offset = 0;
        ipv4[6] = (byte)((flags << 5 & 0xF0) | (offset));

        /*
            8th Byte
            Time to Live (or TTL):  8 bits
                This field indicates the maximum time the datagram is allowed to
                remain in the internet system.  If this field contains the value
                zero, then the datagram must be destroyed.  This field is modified
                in internet header processing.  The time is measured in units of
                seconds, but since every module that processes a datagram must
                decrease the TTL by at least one even if it process the datagram in
                less than a second, the TTL must be thought of only as an upper
                bound on the time a datagram may exist.  The intention is to cause
                undeliverable datagrams to be discarded, and to bound the maximum
                datagram lifetime.

            Project Specification:
                TTL - Implement assuming every packet has a TTL of 50.
         */
        int TTL = 50;
        ipv4[7] = (byte)(TTL);

        /*
            9th Byte
            Protocol:  8 bits
                This field indicates the next level protocol used in the data
                portion of the internet datagram.  The values for various protocols
                are specified in "Assigned Numbers" (RFC 790).
                https://tools.ietf.org/html/rfc790

            Project Specification:
                Protocol - Implement assuming TCP for all packets. TCP is number 6.
         */
        int protocol = 6;
        ipv4[8] = (byte) protocol;

        /*
            10th and 11th Bytes
            Header Checksum (or Checksum):  16 bits
                A checksum on the header only.  Since some header fields change
                (e.g., time to live), this is recomputed and verified at each point
                that the internet header is processed.

                The checksum algorithm is:
                  The checksum field is the 16 bit one's complement of the one's
                  complement sum of all 16 bit words in the header.  For purposes of
                  computing the checksum, the value of the checksum field is zero.

                This is a simple to compute checksum and experimental evidence
                indicates it is adequate, but it is provisional and may be replaced
                by a CRC procedure, depending on further experience.
         */
        int checksum = 0;
        ipv4[9] = (byte) checksum;
        ipv4[10] = (byte) checksum;

        /*
            12th, 13th, 14th and 15th Bytes
            Source Address (or SourceAddr):  32 bits
                The source address.

            Project Specification:
                Source Address - Implement with an IP address of your choice.
                Google.com's IP Address: 172.217.12.78
         */
        ipv4[11] = (byte) (172 & 0xFF);
        ipv4[12] = (byte) (217 & 0xFF);
        ipv4[13] = (byte) 12;
        ipv4[14] = (byte) 78;


        for(int i = 0; i < ipv4.length; i++)
            System.out.println("Packet #" + (i + 1) + ": " + Integer.toBinaryString(ipv4[i]));



        return ipv4;
    }
}
