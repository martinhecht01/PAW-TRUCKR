package ar.edu.itba.paw.models;

public class Pair<A,B>{
    private A key;
    private B value;
    public Pair(A element1, B element2) {
        this.key =element1;
        this.value =element2;
    }

    @Override
    public String toString(){
        return String.format("[%s,%s] ", key, value);
    }

    @Override
    public boolean equals(Object o){

        if (this==o)
            return true;

        if(!(o instanceof Pair))
            return false;

        Pair<?,?> aux = (Pair<?, ?>) o; //se debe castear asi por buena practica

        if (!this.key.equals(aux.key)) //estos equals patean el problema a las clases A y B
            return false;

        return this.value.equals(aux.value);
    }

    public A getKey() {
        return key;
    }

    public B getValue() {
        return value;
    }
}