document.addEventListener('DOMContentLoaded', () => {
  const slides = document.querySelectorAll('.slide');
  const carouselSlides = document.querySelector('.carousel-slides');
  const leftArrow = document.querySelector('.left-arrow');
  const rightArrow = document.querySelector('.right-arrow');
  const ellipsesContainer = document.querySelector('.carousel-ellipses');
  let currentSlide = 0;

  // Create ellipses
  slides.forEach((_, index) => {
    const ellipse = document.createElement('div');
    ellipse.classList.add('ellipse');
    if (index === 0) ellipse.classList.add('active');
    ellipse.addEventListener('click', () => goToSlide(index));
    ellipsesContainer.appendChild(ellipse);
  });

  const updateEllipses = () => {
    document.querySelectorAll('.ellipse').forEach((ellipse, index) => {
      ellipse.classList.toggle('active', index === currentSlide);
    });
  };

  const goToSlide = (index) => {
    currentSlide = index;
    carouselSlides.style.transform = `translateX(-${index * 100}%)`;
    updateEllipses();
  };

  leftArrow.addEventListener('click', () => {
    currentSlide = (currentSlide > 0) ? currentSlide - 1 : slides.length - 1;
    goToSlide(currentSlide);
  });

  rightArrow.addEventListener('click', () => {
    currentSlide = (currentSlide < slides.length - 1) ? currentSlide + 1 : 0;
    goToSlide(currentSlide);
  });
});
