package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CustomerDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.CustomerService;
import util.LocalDateTimeTypeAdapter;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.stream.Collectors;


@WebServlet(value = "/customer")
public class CustomerServlet extends HttpServlet {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();

    private final CustomerService service = new CustomerService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("customer_id"));
        CustomerDto dto = service.get(id);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.write(gson.toJson(dto));
        out.flush();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String json = request.getReader().lines().collect(Collectors.joining());
        CustomerDto customerDTO = gson.fromJson(json, CustomerDto.class);
        service.create(customerDTO);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("customer_id"));
        service.delete(id);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String json = request.getReader().lines().collect(Collectors.joining());
        int id = Integer.parseInt(request.getParameter("customer_id"));
        CustomerDto customerDTO = gson.fromJson(json, CustomerDto.class);
        service.update(id, customerDTO);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}

