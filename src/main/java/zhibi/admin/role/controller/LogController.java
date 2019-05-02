package zhibi.admin.role.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zhibi.admin.role.common.annotation.Operation;
import zhibi.admin.role.common.utils.ReturnUtils;
import zhibi.admin.role.domain.Log;
import zhibi.admin.role.mapper.LogMapper;
import zhibi.admin.role.service.LogService;

/**
 * @author 执笔
 */
@Controller
@RequestMapping("log")
public class LogController {

    @Autowired
    private LogService logService;

    @Operation("查看操作日志")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        return "log/list";
    }

    /**
     * 异步加载list
     *
     * @param log
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public ModelMap list(Log log) {
        ModelMap      map      = new ModelMap();
        PageInfo<Log> pageInfo = logService.selectPage(log);
        map.put("pageInfo", pageInfo);
        map.put("queryParam", log);
        return ReturnUtils.success("加载成功", map);
    }
}
