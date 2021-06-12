public class stateNode {
    public String ch;
    public Integer next1;
    public Integer next2;
    public Integer stateNum;

    public stateNode(String ch_, Integer next1_, Integer next2_, Integer stateNum_) {
        this.ch = ch_;
        this.next1 = next1_;
        this.next2 = next2_;
        this.stateNum = stateNum_;
    }
}
