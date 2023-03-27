// The authors disclaim copyright to this source code

#include <stdlib.h>
#include <string.h>


char *cb(const char *id, const char *event, const char *err, const char *data) {
    char *ret;
    ret = "";
    if (!strcmp(id, "long")) {
        ret = "vibrate:50;add:-;";
    } else if (!strcmp(id, "short"))  {
        ret = "vibrate:30;add:.;";
    } else if (!strcmp(id, "menu")) {
        ret = "add: ;";
    }
    return ret;
}