#include <fcntl.h>
#include <errno.h>
#include <err.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdint.h>
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
unsigned long hash(unsigned char *str) // FIXME: need to take length!
{
  unsigned long hash = 5381;
  int c;
  while ((c = *str++))
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
void Snippet(char*  x0) {
  printf("%s\n","Name,Value");
  char** x2 = (char**)malloc(256 * sizeof(char*));
  int32_t* x3 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t x4 = 0;
  int32_t x5 = 0;
  int32_t* x6 = (int32_t*)malloc(256 * sizeof(int32_t));
  for(int x8=0; x8 < 256; x8++) {
    x6[x8] = -1;
  }
  int32_t* x12 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t x13 = 0;
  int32_t x17 = 0;
  int32_t x14 = open("src/data/t.csv",0);
  int32_t x15 = fsize(x14);
  char* x16 = mmap(0, x15, PROT_READ, MAP_FILE | MAP_SHARED, x14, 0);
  for (;;) {
    int32_t x18 = x17;
    char x19 = x16[x18];
    bool x20 = x19 != ',';
    if (!x20) break;
    x17 += 1;
  }
  int32_t x25 = x17;
  x17 += 1;
  int32_t x28 = x17;
  int32_t x29 = 0;
  for (;;) {
    int32_t x30 = x17;
    char x31 = x16[x30];
    bool x32 = x31 != ',';
    if (!x32) break;
    int32_t x34 = x29;
    int32_t x36 = x17;
    int32_t x35 = x34 * 10;
    char x37 = x16[x36];
    char x38 = x37 - '0';
    int32_t x39 = x35 + x38;
    x29 = x39;
    x17 += 1;
  }
  x17 += 1;
  int32_t x45 = x29;
  int32_t x46 = x17;
  for (;;) {
    int32_t x47 = x17;
    char x48 = x16[x47];
    bool x49 = x48 != '\n';
    if (!x49) break;
    x17 += 1;
  }
  int32_t x54 = x17;
  x17 += 1;
  for (;;) {
    int32_t x58 = x17;
    bool x59 = x58 < x15;
    if (!x59) break;
    int32_t x61 = x17;
    for (;;) {
      int32_t x62 = x17;
      char x63 = x16[x62];
      bool x64 = x63 != ',';
      if (!x64) break;
      x17 += 1;
    }
    int32_t x69 = x17;
    x17 += 1;
    int32_t x73 = x17;
    int32_t x74 = 0;
    for (;;) {
      int32_t x75 = x17;
      char x76 = x16[x75];
      bool x77 = x76 != ',';
      if (!x77) break;
      int32_t x79 = x74;
      int32_t x81 = x17;
      int32_t x80 = x79 * 10;
      char x82 = x16[x81];
      char x83 = x82 - '0';
      int32_t x84 = x80 + x83;
      x74 = x84;
      x17 += 1;
    }
    x17 += 1;
    int32_t x90 = x74;
    int32_t x91 = x17;
    for (;;) {
      int32_t x92 = x17;
      char x93 = x16[x92];
      bool x94 = x93 != '\n';
      if (!x94) break;
      x17 += 1;
    }
    int32_t x99 = x17;
    x17 += 1;
    char* x72 = x16+x61;
    char x103 = x72[0];
    int32_t x104 = (int32_t)x103;
    int32_t x105 = x104 & 255;
    int32_t x70 = x69 - x61;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x106 = x105;
    for (;;) {
      int32_t x107 = x106;
      int32_t x108 = x6[x107];
      char* x110 = x2[x108];
      int32_t x111 = x3[x108];
      bool x109 = x108 != -1;
      bool x113 = x111 == x70;
      bool x128;
      if (x113) {
        int32_t x114 = 0;
        for (;;) {
          int32_t x115 = x114;
          bool x116 = x115 < x111;
          char x117 = x110[x115];
          char x118 = x72[x115];
          bool x119 = x117 == x118;
          bool x120 = x116 && x119;
          if (!x120) break;
          x114 += 1;
        }
        int32_t x125 = x114;
        bool x126 = x125 == x111;
        x128 = x126;
      } else {
        x128 = false;
      }
      bool x129 = !x128;
      bool x130 = x109 && x129;
      if (!x130) break;
      int32_t x132 = x106;
      int32_t x133 = x132 + 1;
      int32_t x134 = x133 & 255;
      x106 = x134;
    }
    int32_t x138 = x106;
    int32_t x139 = x6[x138];
    bool x140 = x139 == -1;
    int32_t x148;
    if (x140) {
      int32_t x141 = x5;
      x2[x141] = x72;
      x3[x141] = x70;
      x5 += 1;
      x6[x138] = x141;
      x12[x141] = 0;
      x148 = x141;
    } else {
      x148 = x139;
    }
    int32_t x150 = x148;
    //#hash_lookup
    int32_t x151 = x12[x150];
    int32_t x152 = x151 + x90;
    x12[x150] = x152;
  }
  int32_t x156 = x5;
  for(int x158=0; x158 < x156; x158++) {
    char* x159 = x2[x158];
    int32_t x160 = x3[x158];
    int32_t x161 = x12[x158];
    int32_t x162 = printll(x159);
    printf(",");
    printf("%d",x161);
    printf("%s\n","");
  }
}
/*****************************************
End of C Generated Code
*******************************************/
