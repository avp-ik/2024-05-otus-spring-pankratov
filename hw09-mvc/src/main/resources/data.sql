insert into authors(full_name)
values ('Пушкин А.С.'), ('Лермонтов М.Ю.'), ('Вертинский А.Н.');

insert into genres(name)
values ('Роман в стихах'), ('Стих'), ('Романс');

insert into books(title, author_id, genre_id)
values ('Евгений Онегин', 1, 1), ('Бородино', 2, 2), ('Танго "Магнолия"', 3, 3);

insert into comments(text, book_id)
values ('Классика русской литературы! Надо брать!', 1), ('Достойнейшее произведение!', 2), ('Отличная книга!', 3);