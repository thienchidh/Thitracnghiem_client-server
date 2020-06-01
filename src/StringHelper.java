import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringHelper {
    private final DateFormat dateFormat;
    private final DateFormat sdf;

    private StringHelper() {
        // example 2019-05-25 12:30:05.80
        dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        sdf = new SimpleDateFormat("dd/MM/yyyy");
    }

    public static StringHelper newInstance() {
        return new StringHelper();
    }

    public String convertToStandardDate(String date) throws ParseException {
        Date parse = dateFormat.parse(date);
        return sdf.format(parse);
    }

    public String convertToString(List<String> list) {
        return convertToString(list, "");
    }

    public String getFirst(Collection<String> collection) {
        if (collection != null && !collection.isEmpty()) {
            Iterator<String> iterator = collection.iterator();
            return iterator.next();
        }
        return "";
    }

    public String convertToString(List<String> list, String concat) {
        StringBuilder sb = new StringBuilder();

        if (list != null) {

            int i = 0, listSize = list.size();
            while (i < listSize - 1) {
                String e = list.get(i);
                sb.append(e).append(concat);
                i++;
            }
            if (listSize >= 1) {
                sb.append(list.get(listSize - 1));
            }
        }

        return sb.toString();
    }

    public ArrayList<String> makeStandardUrls(List<String> urls, String postUrl) {
        ArrayList<String> rs = new ArrayList<>();
        for (String url : urls) {
            rs.add(makeStandardUrl(url, postUrl));
        }

        urls.clear();
        urls.addAll(rs);
        return rs;
    }

    public String makeStandardUrl(String url, String site) {
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            url = site + url;
        }
        return url;
    }

    // for single ton, but now don't use
    private static class Instance {
        private static StringHelper instance = new StringHelper();
    }
}
