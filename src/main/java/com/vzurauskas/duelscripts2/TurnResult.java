package com.vzurauskas.duelscripts2;

import java.util.List;

public class TurnResult {
    private final Strike strike1;
    private final Strike strike2;

    public TurnResult(Strike strike1, Strike strike2) {
        this.strike1 = strike1;
        this.strike2 = strike2;
    }

    public String description() {
        return """
            %s
            %s
            
            %s
            %s
        """.formatted(
            strike1.description(),
            strike2.description(),
            strike1.damageReport(),
            strike2.damageReport()
        );
    }
}
