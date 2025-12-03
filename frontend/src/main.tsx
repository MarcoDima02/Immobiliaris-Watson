/**
 * Node modules
 */
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router';
/**
 * Css
 */
import './index.css';
import 'mapbox-gl/dist/mapbox-gl.css';

/**
 * Router
 */
import router from '@/routes';

/**
 * Components
 */
import { Toaster } from '@/components/ui/sonner';

createRoot(document.getElementById('root')!).render(<StrictMode>
  <RouterProvider router={router}/>
  <Toaster position='top-center'/>
</StrictMode>);
