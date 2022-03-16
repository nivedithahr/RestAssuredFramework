package POJO;

import com.google.gson.Gson;

import java.util.Map;

public class pagesPOJO {
    private  int page;
    private  int per_page;
    private  int total;
    private int total_pages;
    public int getPage()
    {
        return page;
    }
    public void setPage(int page)
    {
        this.page = page;
    }
    public int getPer_page()
    {
        return per_page;
    }
    public void setPer_page(int per_page)
    {
        this.per_page = per_page;
    }
    public int getTotal()
    {
        return total;
    }
    public void setTotal(int total)
    {
        this.total = total;
    }
    public int getTotal_pages()
    {
        return total_pages;
    }
    public void setTotal_pages(int total_pages)
    {
        this.total_pages = total_pages;
    }
    public String getUserPayload(Map<String, String> testData){


        pagesPOJO page = new pagesPOJO();
        page.setPage(Integer.parseInt(testData.get("page")));
        page.setPer_page(Integer.parseInt(testData.get("per_page")));
        page.setTotal(Integer.parseInt(testData.get("total")));
        page.setTotal_pages(Integer.parseInt(testData.get("total_pages")));

        Gson gson = new Gson();
        return gson.toJson(page);

    }
    public String updateUserPayload(Map<String, String> testData)
    {
        pagesPOJO page = new pagesPOJO();
        page.setPage(Integer.parseInt(testData.get("page")));
        page.setTotal(Integer.parseInt(testData.get("total_pages")));
        Gson gson = new Gson();
        return gson.toJson(page);

    }
}
