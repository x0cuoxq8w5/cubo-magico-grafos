public enum Moves {
    F("F"),
    FPrime("F'"),
    F2("F2"),
    U("U"),
    UPrime("U'"),
    U2("U2"),
    R("R"),
    RPrime("R'"),
    R2("R2"),
    L("L"),
    LPrime("L'"),
    L2("L2"),
    D("D"),
    DPrime("D'"),
    D2("D2"),
    B("B"),
    BPrime("B'"),
    B2("B2");

    public final String value;


    Moves(String value) {
        this.value = value;
    }
}
