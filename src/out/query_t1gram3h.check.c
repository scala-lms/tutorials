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
  printf("%s\n", "Word,Value,Phrase,Year,MatchCount,VolumeCount");
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
  int x10 = 0;
  int x11 = 0;
  int* x12 = (int*)malloc(65536 * sizeof(int));
  int* x13 = (int*)malloc(256 * sizeof(int));
  int x14 = open("src/data/words.csv",0);
  int x15 = fsize(x14);
  char* x16 = mmap(0, x15, PROT_READ, MAP_FILE | MAP_SHARED, x14, 0);
  int x17 = 0;
  while (x16[x17] != ',') ({
    x17 = x17 + 1;
  });
  x17 = x17 + 1;
  int x21 = 0;
  while (x16[x17] != '\n') ({
    x21 = x21 * 10 + (int)(x16[x17] - '0');
    x17 = x17 + 1;
  });
  x17 = x17 + 1;
  int x24 = ((int)0L) & 255;
  bool x25 = !true;
  while (x17 < x15) ({
    int x26 = x17;
    while (x16[x17] != ',') ({
      x17 = x17 + 1;
    });
    int x28 = x17;
    x17 = x17 + 1;
    int x29 = 0;
    while (x16[x17] != '\n') ({
      x29 = x29 * 10 + (int)(x16[x17] - '0');
      x17 = x17 + 1;
    });
    x17 = x17 + 1;
    int x31 = x11;
    x7[x31] = x16 + x26;
    x8[x31] = x28 - x26;
    x9[x31] = x29;
    x11 = x11 + 1;
    int x36 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x32 = x24;
      while (({
        x4[x32] != -1 && ({
          x25;
        });
      })) ({
        x32 = (x32 + 1) & 255;
      });
      ((x4[x32] == -1) ? ({
        int x35 = x3;
        x3 = x3 + 1;
        x4[x32] = x35;
        x13[x35] = 0;
        x35;
      }) : x4[x32])//#hash_lookup
      ;});
    int x37 = x13[x36];
    x12[x36 * 256 + x37] = x31;
    x13[x36] = x37 + 1;
  });
  close(x14);
  int x40 = open(x0,0);
  int x41 = fsize(x40);
  char* x42 = mmap(0, x41, PROT_READ, MAP_FILE | MAP_SHARED, x40, 0);
  int x43 = 0;
  while (x43 < x41) ({
    int x44 = x43;
    while (x42[x43] != '\t') ({
      x43 = x43 + 1;
    });
    x43 = x43 + 1;
    int x47 = x43;
    while (x42[x43] != '\t') ({
      x43 = x43 + 1;
    });
    x43 = x43 + 1;
    int x50 = x43;
    while (x42[x43] != '\t') ({
      x43 = x43 + 1;
    });
    x43 = x43 + 1;
    int x53 = x43;
    while (x42[x43] != '\n') ({
      x43 = x43 + 1;
    });
    x43 = x43 + 1;
    int x59 = ({
      //#hash_lookup
      // generated code for hash lookup
      int x56 = x24;
      while (({
        x4[x56] != -1 && ({
          x25;
        });
      })) ({
        x56 = (x56 + 1) & 255;
      });
      x4[x56]//#hash_lookup
      ;});
    ((x59 != -1) ? ({
      char* x60 = x42 + x44;
      char* x61 = x42 + x47;
      char* x62 = x42 + x50;
      char* x63 = x42 + x53;
      int x64 = x59 * 256;
      int x65 = x64 + x13[x59];
      int x66 = x64;
      while (x66 != x65) ({
        int x67 = x12[x66];
        printll(x7[x67]);
        printf(",");
        printf("%d", x9[x67]);
        printf(",");
        printll(x60);
        printf(",");
        printll(x61);
        printf(",");
        printll(x62);
        printf(",");
        printll(x63);
        printf("%s\n", "");
        x66 = x66 + 1;
      });
    }) : ({}));
  });
  close(x40);
}
/*****************************************
End of C Generated Code
*******************************************/
