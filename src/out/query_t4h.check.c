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
  int x2 = 0;
  int x3 = 0;
  int* x4 = (int*)malloc(256 * sizeof(int));
  int x5 = 0;
  while (x5 != 256) ({
    x4[x5] = -1;
    x5 = x5 + 1;
  });
  char** x7 = (char**)malloc(65536 * sizeof(char*));
  int* x8 = (int*)malloc(65536 * sizeof(int));
  int* x9 = (int*)malloc(65536 * sizeof(int));
  char** x10 = (char**)malloc(65536 * sizeof(char*));
  int* x11 = (int*)malloc(65536 * sizeof(int));
  int x12 = 0;
  int x13 = 0;
  int* x14 = (int*)malloc(65536 * sizeof(int));
  int* x15 = (int*)malloc(256 * sizeof(int));
  int x16 = open("src/data/t.csv",0);
  int x17 = fsize(x16);
  char* x18 = mmap(0, x17, PROT_READ, MAP_FILE | MAP_SHARED, x16, 0);
  int x19 = 0;
  while (x18[x19] != ',') ({
    x19 = x19 + 1;
  });
  x19 = x19 + 1;
  int x23 = 0;
  while (x18[x19] != ',') ({
    x23 = x23 * 10 + (int)(x18[x19] - '0');
    x19 = x19 + 1;
  });
  x19 = x19 + 1;
  while (x18[x19] != '\n') ({
    x19 = x19 + 1;
  });
  x19 = x19 + 1;
  int x29 = ((int)0L) & 255;
  bool x30 = !true;
  while (x19 < x17) ({
    int x31 = x19;
    while (x18[x19] != ',') ({
      x19 = x19 + 1;
    });
    int x33 = x19;
    x19 = x19 + 1;
    int x34 = 0;
    while (x18[x19] != ',') ({
      x34 = x34 * 10 + (int)(x18[x19] - '0');
      x19 = x19 + 1;
    });
    x19 = x19 + 1;
    int x36 = x19;
    while (x18[x19] != '\n') ({
      x19 = x19 + 1;
    });
    int x38 = x19;
    x19 = x19 + 1;
    int x39 = x13;
    x7[x39] = x18 + x31;
    x8[x39] = x33 - x31;
    x9[x39] = x34;
    x10[x39] = x18 + x36;
    x11[x39] = x38 - x36;
    x13 = x13 + 1;
    int x44 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x40 = x29;
      while (({
        x4[x40] != -1 && ({
          x30;
        });
      })) ({
        x40 = (x40 + 1) & 255;
      });
      ((x4[x40] == -1) ? ({
        int x43 = x3;
        x3 = x3 + 1;
        x4[x40] = x43;
        x15[x43] = 0;
        x43;
      }) : x4[x40])//#hash_lookup
      ;});
    int x45 = x15[x44];
    x14[x44 * 256 + x45] = x39;
    x15[x44] = x45 + 1;
  });
  close(x16);
  int x48 = 0;
  while (x18[x48] != ',') ({
    x48 = x48 + 1;
  });
  x48 = x48 + 1;
  int x52 = 0;
  while (x18[x48] != ',') ({
    x52 = x52 * 10 + (int)(x18[x48] - '0');
    x48 = x48 + 1;
  });
  x48 = x48 + 1;
  while (x18[x48] != '\n') ({
    x48 = x48 + 1;
  });
  x48 = x48 + 1;
  while (x48 < x17) ({
    int x58 = x48;
    while (x18[x48] != ',') ({
      x48 = x48 + 1;
    });
    x48 = x48 + 1;
    int x61 = 0;
    while (x18[x48] != ',') ({
      x61 = x61 * 10 + (int)(x18[x48] - '0');
      x48 = x48 + 1;
    });
    x48 = x48 + 1;
    while (x18[x48] != '\n') ({
      x48 = x48 + 1;
    });
    x48 = x48 + 1;
    int x70 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x67 = x29;
      while (({
        x4[x67] != -1 && ({
          x30;
        });
      })) ({
        x67 = (x67 + 1) & 255;
      });
      x4[x67]//#hash_lookup
      ;});
    ((x70 != -1) ? ({
      char* x71 = x18 + x58;
      int x72 = x70 * 256;
      int x73 = x72 + x15[x70];
      int x74 = x72;
      while (x74 != x73) ({
        int x75 = x14[x74];
        printll(x7[x75]);
        printf(",");
        printf("%d", x9[x75]);
        printf(",");
        printll(x10[x75]);
        printf(",");
        printll(x71);
        printf("%s\n", "");
        x74 = x74 + 1;
      });
    }) : ({}));
  });
  close(x16);
}
/*****************************************
End of C Generated Code
*******************************************/
