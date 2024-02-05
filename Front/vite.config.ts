import { defineConfig } from 'vitest/config'
import react from '@vitejs/plugin-react'


// https://vitejs.dev/config/
export default defineConfig({
  base: "/paw-2023a-08/",
  test: {
    globals: true,
    environment: "jsdom",
    setupFiles: "/vitest.setup.mjs",
  },
  build: {
    rollupOptions: {
      output: {
        assetFileNames: 'public/[name]-[hash][extname]'
      }
    },
    minify: 'esbuild', // default is 'terser'
  },


  plugins: [react()],
})


