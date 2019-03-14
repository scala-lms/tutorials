#include <fcntl.h>
#include <errno.h>
#include <err.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdint.h>
#include <unistd.h>
#ifndef MAP_FILE
#define MAP_FILE MAP_SHARED
#endif
int fsize(int fd) {
  struct stat stat;
  int res = fstat(fd,&stat);
  return stat.st_size;
}
int printll(char* s) {
  while (*s != '\n' && *s != ',' && *s != '\t') {
    putchar(*s++);
  }
  return 0;
}
long hash(char *str0, int len)
{
  unsigned char* str = (unsigned char*)str0;
  unsigned long hash = 5381;
  int c;
  while ((c = *str++) && len--)
  hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
  return hash;
}
void Snippet(char*);
int main(int argc, char *argv[])
{
  if (argc != 2) {
    printf("usage: query <filename>\n");
    return 0;
  }
  Snippet(argv[1]);
  return 0;
}
/*****************************************
Emitting C Generated Code
*******************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
void Snippet(char* x0) {
  printf("%s\n", "Name,Value,Flag,Name1");
  int x2 = open("src/data/t.csv",0);
  int x3 = fsize(x2);
  char* x4 = mmap(0, x3, PROT_READ, MAP_FILE | MAP_SHARED, x2, 0);
  int x5 = 0;
  while (x4[x5] != ',') ({
    x5 = x5 + 1;
  });
  x5 = x5 + 1;
  int x9 = 0;
  while (x4[x5] != ',') ({
    x9 = x9 * 10 + (int)(x4[x5] - '0');
    x5 = x5 + 1;
  });
  x5 = x5 + 1;
  while (x4[x5] != '\n') ({
    x5 = x5 + 1;
  });
  x5 = x5 + 1;
  while (x5 < x3) ({
    int x15 = x5;
    while (x4[x5] != ',') ({
      x5 = x5 + 1;
    });
    x5 = x5 + 1;
    char* x18 = x4 + x15;
    int x19 = 0;
    while (x4[x5] != ',') ({
      x19 = x19 * 10 + (int)(x4[x5] - '0');
      x5 = x5 + 1;
    });
    x5 = x5 + 1;
    int x21 = x19;
    int x22 = x5;
    while (x4[x5] != '\n') ({
      x5 = x5 + 1;
    });
    x5 = x5 + 1;
    char* x25 = x4 + x22;
    int x26 = 0;
    while (x4[x26] != ',') ({
      x26 = x26 + 1;
    });
    x26 = x26 + 1;
    int x30 = 0;
    while (x4[x26] != ',') ({
      x30 = x30 * 10 + (int)(x4[x26] - '0');
      x26 = x26 + 1;
    });
    x26 = x26 + 1;
    while (x4[x26] != '\n') ({
      x26 = x26 + 1;
    });
    x26 = x26 + 1;
    while (x26 < x3) ({
      int x36 = x26;
      while (x4[x26] != ',') ({
        x26 = x26 + 1;
      });
      x26 = x26 + 1;
      int x39 = 0;
      while (x4[x26] != ',') ({
        x39 = x39 * 10 + (int)(x4[x26] - '0');
        x26 = x26 + 1;
      });
      x26 = x26 + 1;
      while (x4[x26] != '\n') ({
        x26 = x26 + 1;
      });
      x26 = x26 + 1;
      (true ? ({
        printll(x18);
        printf(",");
        printf("%d", x21);
        printf(",");
        printll(x25);
        printf(",");
        printll(x4 + x36);
        printf("%s\n", "");
      }) : ({}));
    });
    close(x2);
  });
  close(x2);
}
/*****************************************
End of C Generated Code
*******************************************/
