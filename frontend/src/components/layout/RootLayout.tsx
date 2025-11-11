/**
 * Node modules
 */
import { Suspense } from 'react';
import { Outlet } from 'react-router';

/**
 * Components
 */
import Header from '@/components/layout/Header';
import Footer from '@/components/layout/Footer';
import Loader from '@/components/Loader';
import { Toaster } from '@/components/ui/sonner';

const RootLayout = () => {
  return (
    <div className="min-h-screen flex flex-col bg-background text-foreground">
      <Header />
      <main className="flex-1 mt-[72px]">
        <Suspense fallback={<Loader />}>
          <Outlet />
        </Suspense>
      </main>
      <Toaster />
      <Footer />
    </div>
  );
};

export default RootLayout;
