/*****************************************
Emitting C Generated Code
*******************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
void Snippet(char*  x0) {
  printf("%s\n","Name,Value,Flag,Name");
  void** x2 = (void**)malloc(65536 * sizeof(void*));
  void** x3 = (void**)malloc(65536 * sizeof(void*));
  void** x4 = (void**)malloc(65536 * sizeof(void*));
  int32_t x5 = 0;
  int32_t x6 = 0;
  int32_t* x7 = (int32_t*)malloc(65536 * sizeof(int32_t));
  void** x8 = (void**)malloc(256 * sizeof(void*));
  int32_t x9 = 0;
  int32_t* x10 = (int32_t*)malloc(256 * sizeof(int32_t));
  for(int x12=0; x12 < 256; x12++) {
    x10[x12] = 0;
  }
  Scanner *x16 = null/*TODO*/;
  char* x17 = null/*TODO*/;
  char* x18 = null/*TODO*/;
  char* x19 = null/*TODO*/;
  for (;;) {
    bool x20 = null/*TODO*/;
    if (!x20) break;
    char* x22 = null/*TODO*/;
    char* x23 = null/*TODO*/;
    char* x24 = null/*TODO*/;
    int32_t x25 = x6;
    x2[x25] = x22;
    x3[x25] = x23;
    x4[x25] = x24;
    x6 += 1;
    //#hash_lookup
    // generated code for hash lookup
    int64_t x30 = x22.##;
    int32_t x31 = (int32_t)x30;
    int32_t x32 = x31 & 255;
    int32_t x33 = x32;
    for (;;) {
      int32_t x34 = x33;
      int32_t x35 = x10[x34];
      void* *x37 = x8[x34];
      bool x38 = x37 == x22;
      bool x36 = x35 != 0;
      bool x39 = true && x38;
      bool x40 = !x39;
      bool x41 = x36 && x40;
      if (!x41) break;
      int32_t x43 = x33;
      int32_t x44 = x43 + 1;
      int32_t x45 = x44 & 255;
      x33 = x45;
    }
    int32_t x49 = x33;
    x8[x49] = x22;
    int32_t x52 = x49;
    //#hash_lookup
    int32_t x53 = x10[x52];
    int32_t x54 = x52 * 256;
    int32_t x55 = x54 + x53;
    x7[x55] = x25;
    int32_t x57 = x53 + 1;
    x10[x52] = x57;
  }
  Scanner *x61 = null/*TODO*/;
  char* x62 = null/*TODO*/;
  char* x63 = null/*TODO*/;
  char* x64 = null/*TODO*/;
  for (;;) {
    bool x65 = null/*TODO*/;
    if (!x65) break;
    char* x67 = null/*TODO*/;
    char* x68 = null/*TODO*/;
    char* x69 = null/*TODO*/;
    //#hash_lookup
    // generated code for hash lookup
    int64_t x70 = x67.##;
    int32_t x71 = (int32_t)x70;
    int32_t x72 = x71 & 255;
    int32_t x73 = x72;
    for (;;) {
      int32_t x74 = x73;
      int32_t x75 = x10[x74];
      void* *x77 = x8[x74];
      bool x78 = x77 == x67;
      bool x76 = x75 != 0;
      bool x79 = true && x78;
      bool x80 = !x79;
      bool x81 = x76 && x80;
      if (!x81) break;
      int32_t x83 = x73;
      int32_t x84 = x83 + 1;
      int32_t x85 = x84 & 255;
      x73 = x85;
    }
    int32_t x89 = x73;
    int32_t x91 = x89;
    //#hash_lookup
    int32_t x92 = x10[x91];
    int32_t x93 = x91 * 256;
    int32_t x94 = x93 + x92;
    for(int x96=x93; x96 < x94; x96++) {
      int32_t x97 = x7[x96];
      void* *x98 = x2[x97];
      void* *x99 = x3[x97];
      void* *x100 = x4[x97];
      printf("\"%s,%s,%s,%s\n\"",x98,x99,x100,x67);
    }
  }
}
/*****************************************
End of C Generated Code
*******************************************/
