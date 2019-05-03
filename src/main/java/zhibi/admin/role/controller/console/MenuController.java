package zhibi.admin.role.controller.console;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import zhibi.admin.role.domain.Menu;
import zhibi.admin.role.mapper.MenuMapper;
import zhibi.admin.role.service.MenuService;
import zhibi.fast.commons.exception.MessageException;
import zhibi.fast.commons.response.JsonResponse;
import zhibi.fast.mybatis.example.MybatisExample;
import zhibi.fast.spring.boot.annotation.Operation;
import zhibi.fast.spring.boot.controller.BaseController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 执笔
 */
@Controller
@RequestMapping("/console/menu")
@RequiresAuthentication
public class MenuController extends BaseController {


    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuMapper  menuMapper;

    @Operation("菜单列表")
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model) {
        ArrayList<Menu> menuLists = new ArrayList<>();
        List<Menu>      lists     = menuService.getChildMenuList(menuLists, 0L);
        model.addAttribute("menus", lists);
        return "console/menu/index";
    }

    @Operation("删除菜单")
    @ResponseBody
    @RequestMapping(value = "/delete", method = {RequestMethod.GET})
    public JsonResponse delete(Long id) {
        menuMapper.deleteById(id);
        return JsonResponse.success("删除成功");
    }

    /**
     * 菜单详情
     *
     * @param id
     * @param parentId
     * @param model
     * @return
     */
    @RequestMapping(value = "/detail/{id}", method = {RequestMethod.GET})
    public String add(@PathVariable Long id, @RequestParam(defaultValue = "0") Long parentId, Model model) {
        Menu menu;
        if (id != 0) {
            menu = menuMapper.selectByPrimaryKey(id);
        } else {
            menu = new Menu();
            menu.setParentId(parentId);
        }
        model.addAttribute("menu", menu);
        return "console/menu/detail";
    }

    @RequestMapping(value = "/merge", method = {RequestMethod.POST})
    @Transactional
    public String save(@Valid Menu menu, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            throw new MessageException(result.getAllErrors().get(0).getDefaultMessage());
        }
        menuService.merge(menu);
        if (menu.getParentId() != 0) {
            //更新父类总数
            MybatisExample example = new MybatisExample()
                    .eq("parent_id", menu.getParentId());
            Integer parentCount = menuMapper.selectCountByExample(example);
            Menu    parentMenu  = menuMapper.selectByPrimaryKey(menu.getParentId());
            parentMenu.setChildNum(parentCount);
            menuMapper.updateByPrimaryKeySelective(parentMenu);
        }
        return redirect("/console/menu/index", "操作成功", attributes);
    }


}
