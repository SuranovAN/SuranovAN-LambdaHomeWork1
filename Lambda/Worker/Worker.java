package Lambda.Worker;

public class Worker {
    @FunctionalInterface
    public interface OnTaskDoneListener {
        void onDone(String result);
    }
    @FunctionalInterface
    public interface OnTaskErrorListener{
        void onError(String error);
    }

    private OnTaskErrorListener errorCallBack;
    private OnTaskDoneListener callBack;

    public Worker(OnTaskDoneListener callBack, OnTaskErrorListener errorCallBack) {
        this.callBack = callBack;
        this.errorCallBack = errorCallBack;
    }

    public void start() {
        for (int i = 0; i < 100; i++) {
            if (i == 33){
                errorCallBack.onError("ERROR i = 33");
                continue;
            }
            callBack.onDone("Task " + i + " is done");
        }
    }
}
