package start.todo.model;

import com.fasterxml.jackson.annotation.JsonView;
import start.todo.model.view.ModelView;

import java.util.LinkedList;
import java.util.List;

public class Statistic {

    @JsonView(ModelView.BasicFields.class)
    private int total;

    @JsonView(ModelView.BasicFields.class)
    private int outDated;

    @JsonView(ModelView.BasicFields.class)
    private int completed;

    private List<Statistic> statistics = new LinkedList<>();

    public Statistic total(int value) {
        this.total = value;
        return this;
    }

    public Statistic outDate(int value) {
        this.outDated = value;
        return this;
    }

    public Statistic completed(int value) {
        this.completed = value;
        return this;
    }

    public List<Statistic> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Statistic> statistics) {
        this.statistics = statistics;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getOutDated() {
        return outDated;
    }

    public void setOutDated(int outDated) {
        this.outDated = outDated;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}
