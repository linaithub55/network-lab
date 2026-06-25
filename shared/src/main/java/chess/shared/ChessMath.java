package chess.shared;

public class ChessMath {
    
    public static boolean isBetween(int val, int a, int b) {
        return val > Math.min(a, b) && val < Math.max(a, b);
    }
    
    public static int checkThreats(int KingX, int KingY, int RookX, int RookY, int BishopX, int BishopY) {
        boolean baseThreatRook = (KingX == RookX) || (KingY == RookY);
        boolean baseThreatBishop = (Math.abs(KingX - BishopX) == Math.abs(KingY - BishopY));

        boolean rookBlocked = false;
        if (baseThreatRook) {
            if (KingX == RookX && BishopX == KingX) {
                rookBlocked = isBetween(BishopY, KingY, RookY);
            } else if (KingY == RookY && BishopY == KingY) {
                rookBlocked = isBetween(BishopX, KingX, RookX);
            }
        }

        boolean bishopBlocked = false;
        if (baseThreatBishop) {
            boolean rookOnSameDiagonal = (Math.abs(KingX - RookX) == Math.abs(KingY - RookY));
            if (rookOnSameDiagonal) {
                bishopBlocked = isBetween(RookX, KingX, BishopX) && isBetween(RookY, KingY, BishopY);
            }
        }

        boolean actualThreatRook = baseThreatRook && !rookBlocked;
        boolean actualThreatBishop = baseThreatBishop && !bishopBlocked;

        if (actualThreatRook && actualThreatBishop) return 3;
        if (actualThreatRook) return 1;
        if (actualThreatBishop) return 2;
        return 0;
    }
}
