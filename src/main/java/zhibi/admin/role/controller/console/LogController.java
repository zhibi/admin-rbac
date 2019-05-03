package zhibi.admin.role.controller.console;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zhibi.admin.role.domain.Log;
import zhibi.admin.role.service.LogService;
import zhibi.fast.commons.response.JsonResponse;
import zhibi.fast.spring.boot.annotation.Operation;

/**
 * @author 执笔
 */
@Controller
@RequestMapping("console/log")
public class LogController {

    @Autowired
    private LogService logService;

    @Operation("查看操作日志")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "console/log/index";
    }

    /**
     * 异步加载list
     *
     * @param log
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public JsonResponse<PageInfo> list(Log log) {
        ModelMap      map      = new ModelMap();
        PageInfo<Log> pageInfo = logService.selectPage(log);
        return JsonResponse.success("加载成功", pageInfo);
    }
}
