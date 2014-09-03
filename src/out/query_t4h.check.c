#include <fcntl.h>
#include <errno.h>
#include <err.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <stdio.h>
#include <stdint.h>
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
  printf("%s\n","Name,Value,Flag,Name1");
  char** x2 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x3 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t* x4 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x5 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x6 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t x7 = 0;
  int32_t x8 = 0;
  int32_t* x9 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t x10 = 0;
  int32_t* x11 = (int32_t*)malloc(256 * sizeof(int32_t));
  for(int x13=0; x13 < 256; x13++) {
    x11[x13] = 0;
  }
  int32_t x20 = 0;
  int32_t x17 = open("src/data/t.csv",0);
  int32_t x18 = fsize(x17);
  char* x19 = mmap(0, x18, PROT_READ, MAP_FILE | MAP_SHARED, x17, 0);
  for (;;) {
    int32_t x21 = x20;
    char x22 = x19[x21];
    bool x23 = x22 != ',';
    if (!x23) break;
    x20 += 1;
  }
  int32_t x28 = x20;
  x20 += 1;
  int32_t x31 = x20;
  int32_t x32 = 0;
  for (;;) {
    int32_t x33 = x20;
    char x34 = x19[x33];
    bool x35 = x34 != ',';
    if (!x35) break;
    int32_t x37 = x32;
    int32_t x39 = x20;
    int32_t x38 = x37 * 10;
    char x40 = x19[x39];
    char x41 = x40 - '0';
    int32_t x42 = x38 + x41;
    x32 = x42;
    x20 += 1;
  }
  x20 += 1;
  int32_t x48 = x32;
  int32_t x49 = x20;
  for (;;) {
    int32_t x50 = x20;
    char x51 = x19[x50];
    bool x52 = x51 != '\n';
    if (!x52) break;
    x20 += 1;
  }
  int32_t x57 = x20;
  x20 += 1;
  int32_t x113 = (int32_t)0L;
  int32_t x114 = x113 & 255;
  bool x119 = !true;
  for (;;) {
    int32_t x61 = x20;
    bool x62 = x61 < x18;
    if (!x62) break;
    int32_t x64 = x20;
    for (;;) {
      int32_t x65 = x20;
      char x66 = x19[x65];
      bool x67 = x66 != ',';
      if (!x67) break;
      x20 += 1;
    }
    int32_t x72 = x20;
    x20 += 1;
    int32_t x76 = x20;
    int32_t x77 = 0;
    for (;;) {
      int32_t x78 = x20;
      char x79 = x19[x78];
      bool x80 = x79 != ',';
      if (!x80) break;
      int32_t x82 = x77;
      int32_t x84 = x20;
      int32_t x83 = x82 * 10;
      char x85 = x19[x84];
      char x86 = x85 - '0';
      int32_t x87 = x83 + x86;
      x77 = x87;
      x20 += 1;
    }
    x20 += 1;
    int32_t x93 = x77;
    int32_t x94 = x20;
    for (;;) {
      int32_t x95 = x20;
      char x96 = x19[x95];
      bool x97 = x96 != '\n';
      if (!x97) break;
      x20 += 1;
    }
    int32_t x102 = x20;
    x20 += 1;
    int32_t x106 = x8;
    char* x75 = x19+x64;
    x2[x106] = x75;
    int32_t x73 = x72 - x64;
    x3[x106] = x73;
    x4[x106] = x93;
    char* x105 = x19+x94;
    x5[x106] = x105;
    int32_t x103 = x102 - x94;
    x6[x106] = x103;
    x8 += 1;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x115 = x114;
    for (;;) {
      int32_t x116 = x115;
      int32_t x117 = x11[x116];
      bool x118 = x117 != 0;
      bool x120 = x118 && x119;
      if (!x120) break;
      int32_t x122 = x115;
      int32_t x123 = x122 + 1;
      int32_t x124 = x123 & 255;
      x115 = x124;
    }
    int32_t x128 = x115;
    int32_t x130 = x128;
    //#hash_lookup
    int32_t x131 = x11[x130];
    int32_t x132 = x130 * 256;
    int32_t x133 = x132 + x131;
    x9[x133] = x106;
    int32_t x135 = x131 + 1;
    x11[x130] = x135;
  }
  int32_t x139 = 0;
  for (;;) {
    int32_t x140 = x139;
    char x141 = x19[x140];
    bool x142 = x141 != ',';
    if (!x142) break;
    x139 += 1;
  }
  int32_t x147 = x139;
  x139 += 1;
  int32_t x149 = x139;
  int32_t x150 = 0;
  for (;;) {
    int32_t x151 = x139;
    char x152 = x19[x151];
    bool x153 = x152 != ',';
    if (!x153) break;
    int32_t x155 = x150;
    int32_t x157 = x139;
    int32_t x156 = x155 * 10;
    char x158 = x19[x157];
    char x159 = x158 - '0';
    int32_t x160 = x156 + x159;
    x150 = x160;
    x139 += 1;
  }
  x139 += 1;
  int32_t x166 = x150;
  int32_t x167 = x139;
  for (;;) {
    int32_t x168 = x139;
    char x169 = x19[x168];
    bool x170 = x169 != '\n';
    if (!x170) break;
    x139 += 1;
  }
  int32_t x175 = x139;
  x139 += 1;
  for (;;) {
    int32_t x179 = x139;
    bool x180 = x179 < x18;
    if (!x180) break;
    int32_t x182 = x139;
    for (;;) {
      int32_t x183 = x139;
      char x184 = x19[x183];
      bool x185 = x184 != ',';
      if (!x185) break;
      x139 += 1;
    }
    int32_t x190 = x139;
    x139 += 1;
    int32_t x194 = x139;
    int32_t x195 = 0;
    for (;;) {
      int32_t x196 = x139;
      char x197 = x19[x196];
      bool x198 = x197 != ',';
      if (!x198) break;
      int32_t x200 = x195;
      int32_t x202 = x139;
      int32_t x201 = x200 * 10;
      char x203 = x19[x202];
      char x204 = x203 - '0';
      int32_t x205 = x201 + x204;
      x195 = x205;
      x139 += 1;
    }
    x139 += 1;
    int32_t x211 = x195;
    int32_t x212 = x139;
    for (;;) {
      int32_t x213 = x139;
      char x214 = x19[x213];
      bool x215 = x214 != '\n';
      if (!x215) break;
      x139 += 1;
    }
    int32_t x220 = x139;
    x139 += 1;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x224 = x114;
    for (;;) {
      int32_t x225 = x224;
      int32_t x226 = x11[x225];
      bool x227 = x226 != 0;
      bool x228 = x227 && x119;
      if (!x228) break;
      int32_t x230 = x224;
      int32_t x231 = x230 + 1;
      int32_t x232 = x231 & 255;
      x224 = x232;
    }
    int32_t x236 = x224;
    int32_t x238 = x236;
    //#hash_lookup
    int32_t x239 = x11[x238];
    int32_t x240 = x238 * 256;
    int32_t x241 = x240 + x239;
    char* x193 = x19+x182;
    for(int x243=x240; x243 < x241; x243++) {
      int32_t x244 = x9[x243];
      char* x245 = x2[x244];
      int32_t x246 = x3[x244];
      int32_t x247 = x4[x244];
      char* x248 = x5[x244];
      int32_t x249 = x6[x244];
      int32_t x250 = printll(x245);
      printf(",");
      printf("%d",x247);
      printf(",");
      int32_t x254 = printll(x248);
      printf(",");
      int32_t x256 = printll(x193);
      printf("%s\n","");
    }
  }
}
/*****************************************
End of C Generated Code
*******************************************/
