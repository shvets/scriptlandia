package struts1.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.commons.beanutils.BeanUtils;

import java.util.List;
import java.util.ArrayList;

import dao.UserServiceImpl;
import struts1.form.UserForm;
import business.User;
import business.UserService;

public class Struts1Action extends DispatchAction {
  private UserService userService = new UserServiceImpl();

  public ActionForward list(ActionMapping mapping, ActionForm form,
                            HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    System.out.println("Executing Struts1Action.list()...");

    List<User> daoList = userService.list();
    List<UserForm> list = new ArrayList<UserForm>();

    for (Object user : daoList) {
      UserForm userForm = new UserForm();

      BeanUtils.copyProperties(userForm, user);

      list.add(userForm);
    }

    request.setAttribute("list", list);

    return mapping.findForward("list");
  }

  public ActionForward view(ActionMapping mapping, ActionForm form,
                            HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    System.out.println("Executing Struts1Action.view()...");

    String id = request.getParameter("id");

    User user = userService.findById(Long.parseLong(id));
    UserForm userForm = new UserForm();

    BeanUtils.copyProperties(userForm, user);

    request.setAttribute("userForm", userForm);

    return mapping.findForward("view");
  }

  public ActionForward create(ActionMapping mapping, ActionForm form,
                            HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    System.out.println("Executing Struts1Action.create()...");

    User user = userService.create();
    UserForm userForm = new UserForm();

    BeanUtils.copyProperties(userForm, user);

    request.setAttribute("userForm", userForm);

    return mapping.findForward("create");
  }

  public ActionForward update(ActionMapping mapping, ActionForm form,
                            HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    System.out.println("Executing Struts1Action.update()...");

    String id = request.getParameter("id");

    User user = userService.findById(Long.parseLong(id));
    UserForm userForm = new UserForm();

    BeanUtils.copyProperties(userForm, user);    

    request.setAttribute("userForm", userForm);

    return mapping.findForward("update");
  }

  public ActionForward delete(ActionMapping mapping, ActionForm form,
                            HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    System.out.println("Executing Struts1Action.delete()...");

    String id = request.getParameter("id");

    userService.delete(Long.parseLong(id));

    return mapping.findForward("delete");
  }

  public ActionForward save(ActionMapping mapping, ActionForm form,
                            HttpServletRequest request, HttpServletResponse response)
          throws Exception {
    System.out.println("Executing Struts1Action.save()...");

    UserForm userForm = (UserForm)request.getAttribute("userForm");

    User user = new User();

    BeanUtils.copyProperties(user, userForm);

    userService.update(user);

    return mapping.findForward("save");
  }

}
