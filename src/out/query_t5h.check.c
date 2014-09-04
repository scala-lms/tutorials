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
  printf("%s\n","Name,Value,Flag,Name");
  char** x2 = (char**)malloc(256 * sizeof(char*));
  int32_t* x3 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t x4 = 0;
  int32_t x5 = 0;
  int32_t* x6 = (int32_t*)malloc(256 * sizeof(int32_t));
  for(int x8=0; x8 < 256; x8++) {
    x6[x8] = -1;
  }
  char** x12 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x13 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t* x14 = (int32_t*)malloc(65536 * sizeof(int32_t));
  char** x15 = (char**)malloc(65536 * sizeof(char*));
  int32_t* x16 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t x17 = 0;
  int32_t x18 = 0;
  int32_t* x19 = (int32_t*)malloc(65536 * sizeof(int32_t));
  int32_t* x20 = (int32_t*)malloc(256 * sizeof(int32_t));
  int32_t x24 = 0;
  int32_t x21 = open("src/data/t.csv",0);
  int32_t x22 = fsize(x21);
  char* x23 = mmap(0, x22, PROT_READ, MAP_FILE | MAP_SHARED, x21, 0);
  for (;;) {
    int32_t x25 = x24;
    char x26 = x23[x25];
    bool x27 = x26 != ',';
    if (!x27) break;
    x24 += 1;
  }
  int32_t x32 = x24;
  x24 += 1;
  int32_t x35 = x24;
  int32_t x36 = 0;
  for (;;) {
    int32_t x37 = x24;
    char x38 = x23[x37];
    bool x39 = x38 != ',';
    if (!x39) break;
    int32_t x41 = x36;
    int32_t x43 = x24;
    int32_t x42 = x41 * 10;
    char x44 = x23[x43];
    char x45 = x44 - '0';
    int32_t x46 = x42 + x45;
    x36 = x46;
    x24 += 1;
  }
  x24 += 1;
  int32_t x52 = x36;
  int32_t x53 = x24;
  for (;;) {
    int32_t x54 = x24;
    char x55 = x23[x54];
    bool x56 = x55 != '\n';
    if (!x56) break;
    x24 += 1;
  }
  int32_t x61 = x24;
  x24 += 1;
  for (;;) {
    int32_t x65 = x24;
    bool x66 = x65 < x22;
    if (!x66) break;
    int32_t x68 = x24;
    for (;;) {
      int32_t x69 = x24;
      char x70 = x23[x69];
      bool x71 = x70 != ',';
      if (!x71) break;
      x24 += 1;
    }
    int32_t x76 = x24;
    x24 += 1;
    int32_t x80 = x24;
    int32_t x81 = 0;
    for (;;) {
      int32_t x82 = x24;
      char x83 = x23[x82];
      bool x84 = x83 != ',';
      if (!x84) break;
      int32_t x86 = x81;
      int32_t x88 = x24;
      int32_t x87 = x86 * 10;
      char x89 = x23[x88];
      char x90 = x89 - '0';
      int32_t x91 = x87 + x90;
      x81 = x91;
      x24 += 1;
    }
    x24 += 1;
    int32_t x97 = x81;
    int32_t x98 = x24;
    for (;;) {
      int32_t x99 = x24;
      char x100 = x23[x99];
      bool x101 = x100 != '\n';
      if (!x101) break;
      x24 += 1;
    }
    int32_t x106 = x24;
    x24 += 1;
    int32_t x110 = x18;
    char* x79 = x23+x68;
    x12[x110] = x79;
    int32_t x77 = x76 - x68;
    x13[x110] = x77;
    x14[x110] = x97;
    char* x109 = x23+x98;
    x15[x110] = x109;
    int32_t x107 = x106 - x98;
    x16[x110] = x107;
    x18 += 1;
    char x117 = x79[0];
    int32_t x118 = (int32_t)x117;
    int32_t x119 = x118 & 255;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x120 = x119;
    for (;;) {
      int32_t x121 = x120;
      int32_t x122 = x6[x121];
      char* x124 = x2[x122];
      int32_t x125 = x3[x122];
      bool x123 = x122 != -1;
      bool x127 = x125 == x77;
      bool x142;
      if (x127) {
        int32_t x128 = 0;
        for (;;) {
          int32_t x129 = x128;
          bool x130 = x129 < x125;
          char x131 = x124[x129];
          char x132 = x79[x129];
          bool x133 = x131 == x132;
          bool x134 = x130 && x133;
          if (!x134) break;
          x128 += 1;
        }
        int32_t x139 = x128;
        bool x140 = x139 == x125;
        x142 = x140;
      } else {
        x142 = false;
      }
      bool x143 = !x142;
      bool x144 = x123 && x143;
      if (!x144) break;
      int32_t x146 = x120;
      int32_t x147 = x146 + 1;
      int32_t x148 = x147 & 255;
      x120 = x148;
    }
    int32_t x152 = x120;
    int32_t x153 = x6[x152];
    bool x154 = x153 == -1;
    int32_t x162;
    if (x154) {
      int32_t x155 = x5;
      x2[x155] = x79;
      x3[x155] = x77;
      x5 += 1;
      x6[x152] = x155;
      x20[x155] = 0;
      x162 = x155;
    } else {
      x162 = x153;
    }
    int32_t x164 = x162;
    //#hash_lookup
    int32_t x165 = x20[x164];
    int32_t x166 = x164 * 256;
    int32_t x167 = x166 + x165;
    x19[x167] = x110;
    int32_t x169 = x165 + 1;
    x20[x164] = x169;
  }
  int32_t x173 = 0;
  for (;;) {
    int32_t x174 = x173;
    char x175 = x23[x174];
    bool x176 = x175 != ',';
    if (!x176) break;
    x173 += 1;
  }
  int32_t x181 = x173;
  x173 += 1;
  int32_t x183 = x173;
  int32_t x184 = 0;
  for (;;) {
    int32_t x185 = x173;
    char x186 = x23[x185];
    bool x187 = x186 != ',';
    if (!x187) break;
    int32_t x189 = x184;
    int32_t x191 = x173;
    int32_t x190 = x189 * 10;
    char x192 = x23[x191];
    char x193 = x192 - '0';
    int32_t x194 = x190 + x193;
    x184 = x194;
    x173 += 1;
  }
  x173 += 1;
  int32_t x200 = x184;
  int32_t x201 = x173;
  for (;;) {
    int32_t x202 = x173;
    char x203 = x23[x202];
    bool x204 = x203 != '\n';
    if (!x204) break;
    x173 += 1;
  }
  int32_t x209 = x173;
  x173 += 1;
  for (;;) {
    int32_t x213 = x173;
    bool x214 = x213 < x22;
    if (!x214) break;
    int32_t x216 = x173;
    for (;;) {
      int32_t x217 = x173;
      char x218 = x23[x217];
      bool x219 = x218 != ',';
      if (!x219) break;
      x173 += 1;
    }
    int32_t x224 = x173;
    x173 += 1;
    int32_t x228 = x173;
    int32_t x229 = 0;
    for (;;) {
      int32_t x230 = x173;
      char x231 = x23[x230];
      bool x232 = x231 != ',';
      if (!x232) break;
      int32_t x234 = x229;
      int32_t x236 = x173;
      int32_t x235 = x234 * 10;
      char x237 = x23[x236];
      char x238 = x237 - '0';
      int32_t x239 = x235 + x238;
      x229 = x239;
      x173 += 1;
    }
    x173 += 1;
    int32_t x245 = x229;
    int32_t x246 = x173;
    for (;;) {
      int32_t x247 = x173;
      char x248 = x23[x247];
      bool x249 = x248 != '\n';
      if (!x249) break;
      x173 += 1;
    }
    int32_t x254 = x173;
    x173 += 1;
    char* x227 = x23+x216;
    char x258 = x227[0];
    int32_t x259 = (int32_t)x258;
    int32_t x260 = x259 & 255;
    int32_t x225 = x224 - x216;
    //#hash_lookup
    // generated code for hash lookup
    int32_t x261 = x260;
    for (;;) {
      int32_t x262 = x261;
      int32_t x263 = x6[x262];
      char* x265 = x2[x263];
      int32_t x266 = x3[x263];
      bool x264 = x263 != -1;
      bool x268 = x266 == x225;
      bool x283;
      if (x268) {
        int32_t x269 = 0;
        for (;;) {
          int32_t x270 = x269;
          bool x271 = x270 < x266;
          char x272 = x265[x270];
          char x273 = x227[x270];
          bool x274 = x272 == x273;
          bool x275 = x271 && x274;
          if (!x275) break;
          x269 += 1;
        }
        int32_t x280 = x269;
        bool x281 = x280 == x266;
        x283 = x281;
      } else {
        x283 = false;
      }
      bool x284 = !x283;
      bool x285 = x264 && x284;
      if (!x285) break;
      int32_t x287 = x261;
      int32_t x288 = x287 + 1;
      int32_t x289 = x288 & 255;
      x261 = x289;
    }
    int32_t x293 = x261;
    int32_t x294 = x6[x293];
    int32_t x296 = x294;
    //#hash_lookup
    int32_t x297 = x20[x296];
    int32_t x298 = x296 * 256;
    int32_t x299 = x298 + x297;
    for(int x301=x298; x301 < x299; x301++) {
      int32_t x302 = x19[x301];
      char* x303 = x12[x302];
      int32_t x304 = x13[x302];
      int32_t x305 = x14[x302];
      char* x306 = x15[x302];
      int32_t x307 = x16[x302];
      int32_t x308 = printll(x303);
      printf(",");
      printf("%d",x305);
      printf(",");
      int32_t x312 = printll(x306);
      printf(",");
      int32_t x314 = printll(x227);
      printf("%s\n","");
    }
  }
}
/*****************************************
End of C Generated Code
*******************************************/
