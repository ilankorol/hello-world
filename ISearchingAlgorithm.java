package algorithms.search;

public interface ISearchingAlgorithm {
    Solution solve(ISearchable s) throws Exception;
    int getNumberOfNodesEvaluated();
    String getName();
}
