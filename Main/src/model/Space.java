package model;

public class Space {

    private Integer actual;
    private final int expected;
    private final boolean fixed;

    public Space (final int expected, final boolean fixed){
        this.expected=expected;
        this.fixed=fixed;
        if (fixed){
            actual = expected; //se a posição for uma fixa, o valor dela não pode ser alterado (o valor previamente colocado)
        }
    }

    public Integer getActual() {
        return actual;
    }

    public void setActual(Integer actual) {
        if (fixed) return; //se for fixo, ele dá um return, não permitindo editar
        this.actual = actual;
    }
    public void clearSpace(){
        setActual(null);
    }
    public int getExpected() {
        return expected;
    }

    public boolean isFixed() {
        return fixed;
    }
    
}
