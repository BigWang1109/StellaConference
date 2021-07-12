package com.wxx.conference.util;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by thinkpad on 2021-2-3.
 */
public class HexConverter {
    public static void hex2File(String hexString, String file)
    {
        // convert hex string to an array of bytes
        byte[] bytes = fromHexString( hexString );

        // build file
        try{
            OutputStream os=new FileOutputStream(file);
            os.write(bytes);
            os.flush();
            os.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static byte[] hex2File(String hexString){
        return fromHexString( hexString );
    }

    private static byte[] correspondingNibble = new byte['f' + 1];

    private static byte[] fromHexString( String s )
    {
        int stringLength = s.length();
        if ( ( stringLength & 0x1 ) != 0 )
        {
            throw new IllegalArgumentException( "fromHexString requires an even number of hex characters" );
        }
        byte[] bytes = new byte[stringLength / 2];

        for ( int i = 0, j = 0; i < stringLength; i += 2, j++ )
        {
            int high = charToNibble( s.charAt( i ) );
            int low = charToNibble( s.charAt( i + 1 ) );
            // You can store either unsigned 0..255 or signed -128..127 bytes in a byte type.
            bytes[ j ] = ( byte ) ( ( high << 4 ) | low );
        }
        return bytes;
    }

    static
    {
        // only 0..9 A..F a..f have meaning. rest are errors.
        for ( int i = 0; i <= 'f'; i++ )
        {
            correspondingNibble[ i ] = -1;
        }
        for ( int i = '0'; i <= '9'; i++ )
        {
            correspondingNibble[ i ] = ( byte ) ( i - '0' );
        }
        for ( int i = 'A'; i <= 'F'; i++ )
        {
            correspondingNibble[ i ] = ( byte ) ( i - 'A' + 10 );
        }
        for ( int i = 'a'; i <= 'f'; i++ )
        {
            correspondingNibble[ i ] = ( byte ) ( i - 'a' + 10 );
        }
    }

    private static int charToNibble( char c )
    {
        if ( c > 'f' )
        {
            throw new IllegalArgumentException( "Invalid hex character: " + c );
        }
        int nibble = correspondingNibble[ c ];
        if ( nibble < 0 )
        {
            throw new IllegalArgumentException( "Invalid hex character: " + c );
        }
        return nibble;
    }
}
