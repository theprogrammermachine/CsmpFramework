package models;

public class Tuple2<A, B, C, D> {
    private A item1;
    private B item2;
    private C item3;
    private D item4;

    public Tuple2(A item1, B item2, C item3, D item4) {
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
    }

    public A getItem1() {
        return item1;
    }

    public void setItem1(A item1) {
        this.item1 = item1;
    }

    public B getItem2() {
        return item2;
    }

    public void setItem2(B item2) {
        this.item2 = item2;
    }

    public C getItem3() {
        return item3;
    }

    public void setItem3(C item3) {
        this.item3 = item3;
    }

    public D getItem4() {
        return item4;
    }

    public void setItem4(D item4) {
        this.item4 = item4;
    }
}
