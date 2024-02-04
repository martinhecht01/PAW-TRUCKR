import { defineConfig } from 'vitest/config'
import react from '@vitejs/plugin-react'


// https://vitejs.dev/config/
export default defineConfig({
  test: {
    globals: true,
    environment: "jsdom",
    setupFiles: "/vitest.setup.mjs",
  },

  plugins: [react()],
})


