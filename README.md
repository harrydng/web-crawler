# Fakebook Crawler

## High-level approach
The crawler works in three main phases: logging in, crawling, and flag detection. To log in, the crawler first sends a GET request to the Fakebook login page to obtain the csrftoken cookie and extract the hidden csrfmiddlewaretoken field from the login form. It then sends a POST request with the username, password, and CSRF token to authenticate and receive a sessionid cookie, which is stored and attached to all subsequent requests.
Once logged in, the crawler uses breadth-first search (BFS) to systematically traverse Fakebook. It maintains a frontier queue of URLs to visit and a visited set to avoid revisiting pages and prevent infinite loops. For each page fetched, it parses all anchor tags to find new links, filters out any URLs that are off-domain or under /accounts/ (to avoid accidentally logging out), and adds unseen paths to the frontier. On every page, the crawler also scans for <h3 class='secret_flag'> tags and extracts the 64-character flag inside, stopping as soon as all 5 flags are found.
All HTTP/1.1 requests and response parsing were implemented from scratch using raw TLS sockets. This included manually handling chunked transfer encoding, parsing and storing cookies from Set-Cookie headers, following 302 redirects, abandoning pages on 403/404 errors, and retrying on 503 responses.

## Challenges faced
- The part where we had to manually parse Set-Cookie headers and re-send them with every request was tougher than expected, since we couldn't use requests or cookielib. We also had to handle duplicate Set-Cookie headers by scanning the raw response bytes with regex.
- The regex posed as a challenge because we didnt know how to write it
- We forgot to account in the LOGOUT page that is in the website and allowed our code to crawl into that, which caused the system to exit, killing its own session mid-crawl, causing all subsequent pages to return no content

## Code test
We tested our code by adding a debug log to check what websites were visited, how many links were found, and check if any flags are being accounted in when the session is done (when all links were visited)