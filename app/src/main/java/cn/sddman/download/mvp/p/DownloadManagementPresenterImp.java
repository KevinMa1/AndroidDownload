package cn.sddman.download.mvp.p;

import org.xutils.x;

import java.util.List;

import cn.sddman.download.R;
import cn.sddman.download.common.Const;
import cn.sddman.download.mvp.e.DownloadTaskEntity;
import cn.sddman.download.mvp.m.DownLoadModel;
import cn.sddman.download.mvp.m.DownLoadModelImp;
import cn.sddman.download.mvp.m.TaskModel;
import cn.sddman.download.mvp.m.TaskModelImp;
import cn.sddman.download.mvp.v.DownloadManagementView;

public class DownloadManagementPresenterImp implements DownloadManagementPresenter {
    private DownloadManagementView downloadManagementView;
    private TaskModel taskModel;
    private DownLoadModel downLoadModel;
    public DownloadManagementPresenterImp(DownloadManagementView downloadManagementView){
        this.downloadManagementView=downloadManagementView;
        taskModel=new TaskModelImp();
        downLoadModel=new DownLoadModelImp();
    }

    @Override
    public void startTask(String url) {
        List<DownloadTaskEntity> tasks=taskModel.findTaskByUrl(url);
        if(tasks!=null && tasks.size()>0){
            DownloadTaskEntity task=tasks.get(0);
            if(task.getmTaskStatus()== Const.DOWNLOAD_CONNECTION
                    || task.getmTaskStatus()== Const.DOWNLOAD_LOADING
                    || task.getmTaskStatus()== Const.DOWNLOAD_FAIL
                    || task.getmTaskStatus()== Const.DOWNLOAD_STOP){
                downloadManagementView.addTaskFail(x.app().getString(R.string.task_earlier_has));
            }else if(task.getmTaskStatus()== Const.DOWNLOAD_SUCCESS){
                downloadManagementView.addTaskFail(x.app().getString(R.string.task_earlier_success));
            }
        }else{
            Boolean b=downLoadModel.startUrlTask(url);
            if(b)
                downloadManagementView.addTaskSuccess();
            else
                downloadManagementView.addTaskFail(x.app().getString(R.string.add_task_fail));

        }
    }
}
